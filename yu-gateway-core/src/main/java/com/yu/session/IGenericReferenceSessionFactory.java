package com.yu.session;

import io.netty.channel.Channel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author yu
 * @description 泛化调用会话工厂接口
 * @date 2024-01-24
 */
public interface IGenericReferenceSessionFactory {

	Future<Channel> openSession() throws ExecutionException, InterruptedException;
}
