package com.lym.netty.client;

import com.lym.LifeCycle;
import com.lym.config.BaseConfig;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.EventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;

import java.io.IOException;

@Slf4j
public class NettyHttpClient implements LifeCycle {

    private AsyncHttpClient asyncHttpClient;

    private DefaultAsyncHttpClientConfig.Builder builder;

    private BaseConfig baseConfig;
    private EventLoopGroup workLoopGroup;

    public NettyHttpClient(BaseConfig baseConfig, EventLoopGroup workLoopGroup) {
        this.baseConfig = baseConfig;
        this.workLoopGroup = workLoopGroup;
        init();
    }


    @Override
    public void init() {
        this.builder = new DefaultAsyncHttpClientConfig.Builder()
                .setFollowRedirect(false)
                .setEventLoopGroup(workLoopGroup)
                .setConnectTimeout(baseConfig.getConnectTimeout())
                .setRequestTimeout(baseConfig.getRequestTimeout())
                .setMaxRequestRetry(baseConfig.getMaxRequestRetry())
                .setAllocator(PooledByteBufAllocator.DEFAULT)
                .setCompressionEnforced(true)
                .setMaxConnections(baseConfig.getMaxConnections())
                .setMaxConnectionsPerHost(baseConfig.getMaxConnectionsPerHost())
                .setPooledConnectionIdleTimeout(baseConfig.getPooledConnectionIdleTimeout());

    }

    @Override
    public void start() {
        this.asyncHttpClient = new DefaultAsyncHttpClient(this.builder.build());
        AsyncHttpHelper.getInstant().init(asyncHttpClient);
    }

    @Override
    public void shutdown() {
        try {
            if (asyncHttpClient != null)
                asyncHttpClient.close();
        } catch (IOException e) {
            log.error("NettyHttpClient shutdown err", e.getCause());
        }
    }
}
