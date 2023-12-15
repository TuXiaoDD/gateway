package com.lym.netty.processor;

import com.lym.context.HttpRequestWrapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyBaseProcessor implements NettyProcessor{

    @Override
    public void start() {
        log.info("NettyBaseProcessor start");

    }

    @Override
    public void shutdown() {

    }

    @Override
    public void process(HttpRequestWrapper wrapper) {

    }
}
