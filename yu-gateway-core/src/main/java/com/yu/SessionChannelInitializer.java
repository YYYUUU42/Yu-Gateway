package com.yu;

import com.yu.handler.SessionServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @author yu
 * @description 用于处理传入的HTTP请求
 * @date 2024-01-24
 */
public class SessionChannelInitializer extends ChannelInitializer<SocketChannel> {


	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		ChannelPipeline line = channel.pipeline();
		//此解码器负责将传入的字节转换为代表HTTP请求的HttpRequest对象。
		line.addLast(new HttpRequestDecoder());
		//此编码器将传出的HttpResponse对象转换为用于在网络上传输的字节。
		line.addLast(new HttpResponseEncoder());
		//用于将多个HTTP消息对象聚合到单个FullHttpRequest或FullHttpResponse中
		//HttpObjectAggregator 用于处理除了 GET 请求外的 POST 请求时候的对象信息
		line.addLast(new HttpObjectAggregator(1024 * 1024));
		//用于拿到HTTP网络请求后，处理我们自己需要的业务逻辑
		line.addLast(new SessionServerHandler());
	}
}
