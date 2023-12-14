package com.lym.netty.handler;

import com.lym.context.HttpRequestWrapper;
import com.lym.processor.NettyProcessor;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyHttpServerHandler extends ChannelInboundHandlerAdapter {


    private NettyProcessor processor;


    public NettyHttpServerHandler(NettyProcessor processor) {
        this.processor = processor;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            HttpRequestWrapper wrapper = new HttpRequestWrapper();
            wrapper.setContext(ctx);
            wrapper.setRequest(request);
            processor.process(wrapper);
        }

    }


}
