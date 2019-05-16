package com.meixun.rpc.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcRequest implements Serializable{
    private static final long serialVersionUID = -6684516625223017186L;

    private String className;

    private String methodName;

    private Class<?> [] paramTypes;

    private Object [] paramValues;
}
