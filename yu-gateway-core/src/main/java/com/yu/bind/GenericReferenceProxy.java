package com.yu.bind;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.dubbo.rpc.service.GenericService;

import java.lang.reflect.Method;

/**
 * @author yu
 * @description 泛化调用静态代理：方便做一些拦截处理
 * 给 http 对应的 RPC 调用，做一层代理控制
 * 每调用到一个 http 对应的网关方法，就会代理的方式调用到 RPC 对应的泛化调用方法上
 * @date 2024-01-24
 */
public class GenericReferenceProxy implements MethodInterceptor {

	/**
	 * RPC 泛化调用服务，用于保存实际的泛化调用服务。
	 */
	private final GenericService genericService;
	/**
	 * RPC 泛化调用方法，用于保存要调用的方法名
	 */
	private final String methodName;

	public GenericReferenceProxy(GenericService genericService, String methodName) {
		this.genericService = genericService;
		this.methodName = methodName;
	}

	/**
	 * 做一层代理控制，后续不止是可以使用 Dubbo 泛化调用，也可以是其他服务的泛化调用
	 *
	 * @param obj         代理对象
	 * @param method      调用的方法对象
	 * @param args        方法参数数组
	 * @param methodProxy 方法的代理对象
	 * @return
	 * @throws Throwable
	 */
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		//获取方法的参数类型数组
		Class<?>[] parameterTypes = method.getParameterTypes();
		//用于保存方法的参数类型的类名
		String[] parameters = new String[parameterTypes.length];

		//循环遍历方法的参数类型数组，将每个参数类型的类名保存到 parameters 数组中
		for (int i = 0; i < parameterTypes.length; i++) {
			parameters[i] = parameterTypes[i].getName();
		}

		//methodName: 要调用的方法名	parameters: 方法的参数类型的类名数组		arg: 方法的实际参数数组
		// 举例：genericService.$invoke("sayHi", new String[]{"java.lang.String"}, new Object[]{"world"});
		return genericService.$invoke(methodName, parameters, args);
	}
}
