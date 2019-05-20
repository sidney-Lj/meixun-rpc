package com.meixun.rpc.server;

import com.meixun.rpc.common.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

public class ProcessorHandler implements Runnable{

    private Socket socket;

    private Map<String,Object> handlerMap;


    public ProcessorHandler(Socket socket, Map<String,Object> handlerMap) {
        this.socket = socket;
        this.handlerMap = handlerMap;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            //反序列化
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object result = invoke(rpcRequest);
            //将结果返回给客户端
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object invoke(RpcRequest rpcRequest) throws ClassNotFoundException, InstantiationException, IllegalAccessException,NoSuchMethodException, InvocationTargetException {
        System.out.println("服务端开始调用------");
        Class[] paramTypes = rpcRequest.getParamTypes();
        Object[] paramValues = rpcRequest.getParamValues();
        //从Map中获得Service（根据客户端请求的ServiceName，获得相应的服务），依旧是通过反射发起调用
        Object service = handlerMap.get(rpcRequest.getClassName());
        Method method = service.getClass().getMethod(rpcRequest.getMethodName(), paramTypes);
        return method.invoke(service, paramValues);
    }
}
