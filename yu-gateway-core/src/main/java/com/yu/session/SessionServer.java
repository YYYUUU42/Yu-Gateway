package com.yu.session;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.util.concurrent.Callable;

/**
 * @author yu
 * @description 实现了Socket服务器的启动
 * @date 2024-01-24
 */
public class SessionServer implements Callable<Channel> {

	private final Logger logger = LoggerFactory.getLogger(SessionServer.class);

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

	private Configuration configuration;

	public SessionServer(Configuration configuration) {
		this.configuration = configuration;
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
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(boss, work)
					//指定使用NIO传输的 NioServerSocketChannel 类来接受新的连接
					.channel(NioServerSocketChannel.class)
					//设置Socket的参数，其中 SO_BACKLOG 表示请求连接的最大队列长度
					.option(ChannelOption.SO_BACKLOG, 128)
					//指定 SessionChannelInitializer 用于处理新连接数据
					.childHandler(new SessionChannelInitializer(configuration));

			channelFuture = serverBootstrap.bind(new InetSocketAddress(7397)).syncUninterruptibly();
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
