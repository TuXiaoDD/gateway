package com.lym.netty;

import com.lym.Bootstrap;
import com.lym.LifeCycle;
import com.lym.config.BaseConfig;
import com.lym.netty.handler.NettyHttpConnectionHandler;
import com.lym.netty.handler.NettyHttpServerHandler;
import com.lym.processor.NettyProcessor;
import com.lym.util.RemotingUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class NettyHttpServer implements LifeCycle {

    private int port = 9999;

    private ServerBootstrap serverBootstrap;
    private EventLoopGroup bossLoopGroup;
    private EventLoopGroup workLoopGroup;
    private final NettyProcessor nettyProcessor;

    private final BaseConfig baseConfig;

    public NettyHttpServer(BaseConfig config, NettyProcessor nettyProcessor) {
        this.baseConfig = config;
        this.nettyProcessor = nettyProcessor;
        if (baseConfig.getPort() > 0 && baseConfig.getPort() < 65535) {
            this.port = baseConfig.getPort();
        }
        init();
    }

    @Override
    public void init() {
        serverBootstrap = new ServerBootstrap();
        if (useEpoll()) {
            this.bossLoopGroup = new EpollEventLoopGroup(baseConfig.getBossGroupNum());
            this.workLoopGroup = new EpollEventLoopGroup(baseConfig.getWorkGroupNum());
        } else {
            this.bossLoopGroup = new NioEventLoopGroup(baseConfig.getBossGroupNum());
            this.workLoopGroup = new NioEventLoopGroup(baseConfig.getWorkGroupNum());
        }

    }

    @Override
    public void start() {
        ServerBootstrap bootstrap = serverBootstrap.group(bossLoopGroup, workLoopGroup)
                .channel(useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)// boss中等待的+boss accept的
                .option(ChannelOption.SO_REUSEADDR, true)// tcp端口重绑定
                .option(ChannelOption.SO_KEEPALIVE, false)//保活
                .childOption(ChannelOption.TCP_NODELAY, true)// 禁用Nagle算法,小数据合并
                .childOption(ChannelOption.SO_SNDBUF, 65535)
                .childOption(ChannelOption.SO_RCVBUF, 65535)
                .localAddress(new InetSocketAddress(this.port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new HttpServerCodec())
                                .addLast(new HttpObjectAggregator(baseConfig.getMaxContentLength()))
                                .addLast(new HttpServerExpectContinueHandler())
                                .addLast(new NettyHttpConnectionHandler())
                                .addLast(new NettyHttpServerHandler(nettyProcessor));
                    }
                });
        if (baseConfig.isNettyAllocator()) {
            bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        }

        try {
            bootstrap.bind().sync();
            log.info("NettyHttpServer start");
        } catch (InterruptedException e) {
            log.error("netty server start sync fail ", e.getCause());
            throw new RuntimeException("netty server start fail", e);
        }

    }

    @Override
    public void shutdown() {
        if (bossLoopGroup != null) bossLoopGroup.shutdownGracefully();
        if (workLoopGroup != null) workLoopGroup.shutdownGracefully();
    }

    public boolean useEpoll() {
        return baseConfig.isUseEpoll() && RemotingUtil.isLinuxPlatform() && Epoll.isAvailable();
    }


}
