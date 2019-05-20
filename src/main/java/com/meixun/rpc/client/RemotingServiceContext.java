package com.meixun.rpc.client;

import com.meixun.rpc.client.proxy.RpcProxy;

public class RemotingServiceContext {
    public static Object getRemotingService(Class iface){
        RpcProxy proxyHandler = new RpcProxy();
        return proxyHandler.bind(iface);
    }
}
