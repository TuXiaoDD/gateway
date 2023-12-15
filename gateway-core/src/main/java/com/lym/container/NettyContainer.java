package com.lym.container;

import com.lym.LifeCycle;
import com.lym.config.BaseConfig;
import com.lym.netty.NettyHttpServer;
import com.lym.netty.client.NettyHttpClient;
import com.lym.netty.processor.NettyBaseProcessor;
import com.lym.netty.processor.NettyProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * 容器启动类
 */
@Slf4j
public class NettyContainer implements LifeCycle {

    private NettyProcessor nettyProcessor;

    private NettyHttpServer nettyHttpServer;
    private NettyHttpClient nettyHttpClient;

    private BaseConfig baseConfig;

    public NettyContainer(BaseConfig baseConfig) {
        this.baseConfig = baseConfig;
        init();
    }

    @Override
    public void init() {
        // todo 扩展
        nettyProcessor = new NettyBaseProcessor();
        nettyHttpServer = new NettyHttpServer(baseConfig, nettyProcessor);
        nettyHttpClient = new NettyHttpClient(baseConfig, nettyHttpServer.getWorkLoopGroup());
    }

    @Override
    public void start() {
        log.info("NettyContainer start");
        nettyProcessor.start();
        nettyHttpServer.start();
        nettyHttpClient.start();
    }

    @Override
    public void shutdown() {
        nettyProcessor.shutdown();
        nettyHttpServer.shutdown();
        nettyHttpClient.shutdown();
    }
}
