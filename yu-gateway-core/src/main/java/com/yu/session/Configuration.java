package com.yu.session;

import com.yu.bind.IGenericReference;
import com.yu.bind.MapperRegistry;
import com.yu.mapper.HttpStatement;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yu
 * @description 会话生命周期配置项
 * @date 2024-01-24
 */
public class Configuration {

	private final MapperRegistry mapperRegistry = new MapperRegistry(this);

	private final Map<String, HttpStatement> httpStatements = new HashMap<>();

	/**
	 * RPC 应用服务配置项 api-gateway-test
	 */
	private final Map<String, ApplicationConfig> applicationConfigMap = new HashMap<>();

	/**
	 * RPC 注册中心配置项 zookeeper://127.0.0.1:2181
	 */
	private final Map<String, RegistryConfig> registryConfigMap = new HashMap<>();

	/**
	 * RPC 泛化服务配置项 cn.bugstack.gateway.rpc.IActivityBooth
	 */
	private final Map<String, ReferenceConfig<GenericService>> referenceConfigMap = new HashMap<>();

	public Configuration() {
		// TODO 后期从配置中获取
		ApplicationConfig application = new ApplicationConfig();
		application.setName("api-gateway-test");
		application.setQosEnable(false);

		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("zookeeper://127.0.0.1:2181");
		registry.setRegister(false);

		ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
		reference.setInterface("cn.bugstack.gateway.rpc.IActivityBooth");
		reference.setVersion("1.0.0");
		reference.setGeneric("true");

		applicationConfigMap.put("api-gateway-test", application);
		registryConfigMap.put("api-gateway-test", registry);
		referenceConfigMap.put("cn.bugstack.gateway.rpc.IActivityBooth", reference);
	}

	public ApplicationConfig getApplicationConfig(String applicationName) {
		return applicationConfigMap.get(applicationName);
	}

	public RegistryConfig getRegistryConfig(String applicationName) {
		return registryConfigMap.get(applicationName);
	}

	public ReferenceConfig<GenericService> getReferenceConfig(String interfaceName) {
		return referenceConfigMap.get(interfaceName);
	}

	public void addMapper(HttpStatement httpStatement) {
		mapperRegistry.addMapper(httpStatement);
	}

	public IGenericReference getMapper(String uri, GatewaySession gatewaySession) {
		return mapperRegistry.getMapper(uri, gatewaySession);
	}

	public void addHttpStatement(HttpStatement httpStatement) {
		httpStatements.put(httpStatement.getUri(), httpStatement);
	}

	public HttpStatement getHttpStatement(String uri) {
		return httpStatements.get(uri);
	}

}
