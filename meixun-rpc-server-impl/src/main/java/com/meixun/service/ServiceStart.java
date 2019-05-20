package com.meixun.service;

import com.meixun.rpc.server.RpcServer;

public class ServiceStart {
    public static void main(String[] args) {
        RpcServer rpcServer = new RpcServer();
        rpcServer.bind(new HelloServiceImpl());
        rpcServer.publisher();
    }
}
