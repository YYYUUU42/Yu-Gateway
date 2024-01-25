package com.yu.datasource;

/**
 * @author yu
 * @description 连接接口
 * @date 2024-01-25
 */
public interface Connection {

    Object execute(String method, String[] parameterTypes, String[] parameterNames, Object[] args);

}