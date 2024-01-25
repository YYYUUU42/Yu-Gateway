package com.yu.socket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public abstract class BaseHandler<T> extends SimpleChannelInboundHandler<T> {

	/**
	 * 会在有数据可读的时候被调用
	 * @throws Exception
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {
		session(ctx, ctx.channel(), msg);
	}

	/**
	 * 用于处理会话
	 */
	protected abstract void session(ChannelHandlerContext ctx, final Channel channel, T request);

}
