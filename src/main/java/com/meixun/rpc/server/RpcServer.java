package com.meixun.rpc.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RpcServer {

    private static final int DEFULT_PORT = 8080;

    public void publisher() {
        //启动一个服务监听
        //获取端口
        try {
            ServerSocket serverSocket = new ServerSocket(DEFULT_PORT);
            while (true){
                //通过ServerSocket获取请求
                Socket socket = serverSocket.accept();
                new Thread(new ProcessorHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
