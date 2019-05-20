package com.meixun.rpc.server;

import com.meixun.rpc.server.annotation.RpcService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RpcServer {

    private static final int DEFULT_PORT = 8080;

    private static final Map<String,Object> HANDLER_MAPPING = new HashMap<>();

    public void publisher() {
        //启动一个服务监听
        //获取端口
        try {
            ServerSocket serverSocket = new ServerSocket(DEFULT_PORT);
            while (true){
                //通过ServerSocket获取请求
                Socket socket = serverSocket.accept();
                new Thread(new ProcessorHandler(socket, HANDLER_MAPPING)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bind(Object... services){
        for (Object service : services) {
            //获取发布的服务接口
            RpcService rpcService = service.getClass().getAnnotation(RpcService.class);
            if (rpcService == null){
                continue;
            }
            //发布接口的class
            String serviceName = rpcService.value().getName();
            //将serviceName和service进行绑定
            HANDLER_MAPPING.put(serviceName,service);
        }
    }

}
