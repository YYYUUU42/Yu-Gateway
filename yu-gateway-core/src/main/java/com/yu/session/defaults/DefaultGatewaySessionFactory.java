package com.yu.session.defaults;

import com.yu.session.Configuration;
import com.yu.session.GatewaySession;
import com.yu.session.GatewaySessionFactory;

/**
 * @author yu
 * @description 默认网关会话工厂
 * @date 2024-01-25
 */
public class DefaultGatewaySessionFactory implements GatewaySessionFactory {

	private final Configuration configuration;

	public DefaultGatewaySessionFactory(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public GatewaySession openSession() {
		return new DefaultGatewaySession(configuration);
	}

}
