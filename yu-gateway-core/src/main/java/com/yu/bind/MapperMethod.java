package com.yu.bind;

import com.yu.mapper.HttpCommandType;
import com.yu.session.Configuration;
import com.yu.session.GatewaySession;

import java.lang.reflect.Method;

/**
 * @author yu
 * @description 绑定调用方法
 * @date 2024-01-25
 */
public class MapperMethod {

    private String uri;
    private final HttpCommandType command;

    public MapperMethod(String uri, Method method, Configuration configuration) {
        this.uri = uri;
        this.command = configuration.getHttpStatement(uri).getHttpCommandType();
    }

    public Object execute(GatewaySession session, Object args) {
        Object result = null;
        switch (command) {
            case GET:
                result = session.get(uri, args);
                break;
            case POST:
                break;
            case PUT:
                break;
            case DELETE:
                break;
            default:
                throw new RuntimeException("Unknown execution method for: " + command);
        }
        return result;
    }

}