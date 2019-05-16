package com.meixun.rpc.client;

import com.meixun.rpc.client.proxy.RpcProxy;

public class RemotingServiceContext {
    public Object getRemotingService(Class clazz){
        RpcProxy proxyHandler = new RpcProxy();
        return proxyHandler.bind(clazz);
    }
}
