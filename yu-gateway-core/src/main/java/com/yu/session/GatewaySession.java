package com.yu.session;

import com.yu.bind.IGenericReference;

/**
 * @author yu
 * @description 用户处理网关 HTTP 请求
 * @date 2024-01-25
 */
public interface GatewaySession {

	Object get(String uri, Object parameter);

	IGenericReference getMapper(String uri);

	Configuration getConfiguration();

}