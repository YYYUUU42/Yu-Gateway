package com.yu.socket;

import com.yu.session.defaults.DefaultGatewaySessionFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

/**
 * @author yu
 * @description 网关会话服务
 * @date 2024-01-25
 */
public class GatewaySocketServer implements Callable<Channel> {
	private final Logger logger = LoggerFactory.getLogger(GatewaySocketServer.class);

	/**
	 * 创建用于处理连接的 boss 线程组
	 * 这里使用了 NioEventLoopGroup，表示基于NIO的事件循环组
	 * 1 是传递给构造函数的参数，表示线程池中的线程数
	 */
	private final EventLoopGroup boss = new NioEventLoopGroup(1);

	/**
	 * 创建用于处理I/O操作的 worker 线程组
	 */
	private final EventLoopGroup work = new NioEventLoopGroup();

	/**
	 * 保存创建的通道对象，以便后续可以对其进行操作
	 */
	private Channel channel;

	private DefaultGatewaySessionFactory gatewaySessionFactory;

	public GatewaySocketServer(DefaultGatewaySessionFactory gatewaySessionFactory) {
		this.gatewaySessionFactory = gatewaySessionFactory;
	}

	/**
	 * 用于启动Socket服务器
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public Channel call() throws Exception {
		//用于保存异步操作的结果
		ChannelFuture channelFuture = null;
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(boss, work)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 128)
					.childHandler(new GatewayChannelInitializer(gatewaySessionFactory));

			channelFuture = b.bind(new InetSocketAddress(7397)).syncUninterruptibly();
			this.channel = channelFuture.channel();
		} catch (Exception e) {
			logger.error("socket server start error.", e);
		} finally {
			if (channelFuture != null && channelFuture.isSuccess()) {
				logger.info("socket server start done.");
			} else {
				logger.error("socket server start error.");
			}
		}

		return channel;
	}
}
