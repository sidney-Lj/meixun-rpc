package com.meixun.client;

import com.meixun.rpc.client.RemotingServiceContext;
import com.meixun.service.HelloService;

public class RpcTest {
    public static void main(String[] args) {
        HelloService helloService = (HelloService) RemotingServiceContext.getRemotingService(HelloService.class);
        helloService.say("xiaoming");
        System.out.println(helloService.getNameById(123456L));
    }
}
