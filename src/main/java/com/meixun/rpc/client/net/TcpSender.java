package com.meixun.rpc.client.net;

import com.meixun.rpc.common.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TcpSender {
    private String serviceAddress;

    private Socket newSocket() {
        System.out.println("准备创建Socket连接，"+serviceAddress);
        String[] split = serviceAddress.split(":");
        try {
            Socket socket = new Socket(split[0], Integer.parseInt(split[1]));
            return socket;
        } catch (IOException e) {
            throw new RuntimeException("Socket连接创建失败！" + serviceAddress);
        }
    }

    public TcpSender(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public Object send(RpcRequest rpcRequest) {
        Socket socket = newSocket();
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(rpcRequest);
                outputStream.flush();
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                Object result = inputStream.readObject();
                inputStream.close();
                outputStream.close();
                return result;
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("发起远程调用异常！",e);
            } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
