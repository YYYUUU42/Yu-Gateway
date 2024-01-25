package com.yu.socket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author yu
 * @description 数据处理器基累
 * @date 2024-01-25
 */
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
