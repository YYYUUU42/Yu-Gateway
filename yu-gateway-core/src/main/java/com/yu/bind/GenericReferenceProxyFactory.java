package com.yu.bind;

import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import org.apache.dubbo.rpc.service.GenericService;
import org.objectweb.asm.Type;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yu
 * @description 泛化调用静态代理工厂
 * @date 2024-01-24
 */
public class GenericReferenceProxyFactory {
	/**
	 * RPC 泛化调用服务
	 */
	private final GenericService genericService;

	/**
	 * 用于存储已经创建的泛化调用代理对象，以避免重复创建
	 */
	private final Map<String, IGenericReference> genericReferenceCache = new ConcurrentHashMap<>();

	public GenericReferenceProxyFactory(GenericService genericService) {
		this.genericService = genericService;
	}

	/**
	 * 根据指定的方法名创建泛化调用的代理对象
	 *
	 * @param method
	 * @return
	 */
	public IGenericReference newInstance(String method) {
		//如果 genericReferenceCache 中已经存在指定方法名的代理对象，则直接返回缓存中的对象		否则创建新的代理对象
		IGenericReference iGenericReference = genericReferenceCache.computeIfAbsent(method, k -> {
			//用于处理泛化调用的代理，该代理对象需要知道具体的泛化调用服务和方法名
			GenericReferenceProxy genericReferenceProxy = new GenericReferenceProxy(genericService, method);

			//生成动态接口
			InterfaceMaker interfaceMaker = new InterfaceMaker();
			//向动态接口中添加一个方法签名，该方法的签名由 method 参数指定。在这里，使用了 String 类型的参数和返回类型
			interfaceMaker.add(new Signature(method, Type.getType(String.class), new Type[]{Type.getType(String.class)}), null);
			Class<?> interfaceClass = interfaceMaker.create();

			//这个代理对象既实现了 IGenericReference 接口，也实现了动态生成的接口，因此可以调用这两个接口中的方法
			//在实际的调用过程中，代理对象的方法调用会被 genericReferenceProxy 处理，执行相应的逻辑。
			Enhancer enhancer = new Enhancer();

			enhancer.setSuperclass(Object.class);
			// interfaceClass    根据泛化调用注册信息创建的接口，建立 http -> rpc 关联
			enhancer.setInterfaces(new Class[]{IGenericReference.class, interfaceClass});
			enhancer.setCallback(genericReferenceProxy);
			return (IGenericReference) enhancer.create();
		});

		return iGenericReference;
	}

}
