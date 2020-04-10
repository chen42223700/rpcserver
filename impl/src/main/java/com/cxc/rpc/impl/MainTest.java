package com.cxc.rpc.impl;


import com.csc.rpc.rpcinterface.service.HelloService;
import com.cxc.rpc.impl.service.HelloServiceImpl;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Description
 * @Author Link.Chen
 * @Date2020/4/3 14:52
 **/
public class MainTest {

    public static void main(String[] args) throws Exception{
//        HelloService service = new HelloServiceImpl();
//        System.out.println(service.sayHello("rpc"));
//        String service = "com.csc.rpc.rpcinterface.service.HelloService";
        String service = "com.cxc.rpc.impl.service.HelloServiceImpl";
        String methodName = "sayHello";
//        String methodName = "sayHelloWithOutArgs";
        Class clazz = Class.forName(service);
        Object o = clazz.newInstance();
        Method method=clazz.getMethod(methodName, String.class);
        System.out.println(method.invoke(o,"rpc"));
        return;
//        Class<?>[] types=new Class[1];
//        types[0] = "rpc".getClass();
//        try {
//            Class clazz = service.getClass();
//            Method method=clazz.getMethod(methodName,types);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        System.out.println("123");
    }


}
