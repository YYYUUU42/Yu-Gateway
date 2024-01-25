package com.yu.session;

/**
 * @author yu
 * @description 网关会话工厂接口
 * @date 2024-01-25
 */
public interface GatewaySessionFactory {

	GatewaySession openSession();

}