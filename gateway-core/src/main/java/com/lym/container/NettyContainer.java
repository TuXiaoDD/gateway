package com.lym.container;

import com.lym.LifeCycle;
import com.lym.config.BaseConfig;
import com.lym.netty.NettyHttpServer;
import com.lym.processor.NettyBaseProcessor;
import com.lym.processor.NettyProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * 容器
 */
@Slf4j
public class NettyContainer implements LifeCycle {

    private NettyProcessor nettyProcessor;

    private NettyHttpServer nettyHttpServer;

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

    }

    @Override
    public void start() {
        log.info("NettyContainer start");
        nettyProcessor.start();
        nettyHttpServer.start();
    }

    @Override
    public void shutdown() {
        nettyProcessor.shutdown();
        nettyHttpServer.shutdown();
    }
}
