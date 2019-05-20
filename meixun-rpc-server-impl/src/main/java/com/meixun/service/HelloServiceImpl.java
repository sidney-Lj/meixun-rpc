package com.meixun.service;

import com.meixun.rpc.server.annotation.RpcService;

@RpcService(HelloService.class)
public class HelloServiceImpl implements  HelloService {
    @Override
    public void say(String name) {
        System.out.println("hello,  " + name );
    }

    @Override
    public String getNameById(Long id) {
        return "id == " + id + "; name==xiaoming";
    }
}
