package com.lym.netty.processor;

import com.lym.config.BaseConfig;
import com.lym.context.HttpRequestWrapper;

public interface NettyProcessor {

    void start();

    void shutdown();

    void process(HttpRequestWrapper wrapper);

}
