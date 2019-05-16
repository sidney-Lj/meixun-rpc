package com.meixun.rpc.client.proxy;

import com.meixun.rpc.client.net.TcpSender;
import com.meixun.rpc.common.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcProxy implements InvocationHandler {

    private Class target;
    public Object bind(Class target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClassLoader(), target.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before method excute!");
        method.invoke(target, args);
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(target.getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParamTypes(method.getParameterTypes());
        rpcRequest.setParamValues(args);
        TcpSender tcpSender = new TcpSender("127.0.0.1:8080");
        return tcpSender.send(rpcRequest);
    }
}
