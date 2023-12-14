package com.lym.netty.handler;

import com.lym.util.RemotingUtil;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyHttpConnectionHandler extends ChannelDuplexHandler {


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        String address = RemotingUtil.parseRemoteAddress(ctx.channel());
        log.info("NettyHttpConnectionHandler channelRegistered address {}", address);
        super.channelRegistered(ctx);
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        String address = RemotingUtil.parseRemoteAddress(ctx.channel());
        log.info("NettyHttpConnectionHandler channelUnregistered address {}", address);
        super.channelUnregistered(ctx);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String address = RemotingUtil.parseRemoteAddress(ctx.channel());
        log.info("NettyHttpConnectionHandler channelActive address {}", address);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String address = RemotingUtil.parseRemoteAddress(ctx.channel());
        log.info("NettyHttpConnectionHandler channelInactive address {}", address);
        super.channelInactive(ctx);
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) { // 空闲事件
            IdleStateEvent event = (IdleStateEvent)evt;
            if(IdleState.ALL_IDLE.equals(event.state())){
                String address = RemotingUtil.parseRemoteAddress(ctx.channel());
                log.info("NettyHttpConnectionHandler userEventTriggered address {} No data was either received or sent", address);
                ctx.channel().close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String address = RemotingUtil.parseRemoteAddress(ctx.channel());
        log.info("NettyHttpConnectionHandler exceptionCaught address {} error {}", address, cause);
        super.exceptionCaught(ctx, cause);
    }
}
