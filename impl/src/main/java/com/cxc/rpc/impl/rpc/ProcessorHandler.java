package com.cxc.rpc.impl.rpc;

import com.alibaba.fastjson.JSON;
import com.csc.rpc.rpcframe.bean.RpcRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Objects;

/**
 * @Description
 * @Author Link.Chen
 * @Date2020/4/2 16:34
 **/
public class ProcessorHandler implements Runnable{

    private Socket socket;

    private ApplicationContext applicationContext;

    public ProcessorHandler(Socket socket, ApplicationContext applicationContext) {
        this.socket = socket;
        this.applicationContext = applicationContext;
    }



    @Override
    public void run() {
        //处理请求
        ObjectInputStream inputStream=null;
        try {
            //获取客户端的输入流
            inputStream=new ObjectInputStream(socket.getInputStream());
            //反序列化远程传输的对象RpcRequest
            Object object = inputStream.readObject();
            System.out.println(object.toString());
            RpcRequest request = JSON.parseObject(object.toString(), RpcRequest.class);
            Object result=invoke(request); //通过反射去调用本地的方法

            //通过输出流讲结果输出给客户端
            ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(result);
            outputStream.flush();
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(!Objects.isNull(inputStream)){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private Object invoke(RpcRequest request) {
        try {
            //一下均为反射操作，目的是通过反射调用服务
            Object[] args=request.getParameters();
            Class<?>[] types=new Class[args.length];
            for(int i=0;i<args.length;i++){
                types[i]=args[i].getClass();
            }
            Object o = applicationContext.getBean(Class.forName(request.getClassName()));
            Method method= o.getClass().getMethod(request.getMethodName(),types);
            return method.invoke(o,args);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("");
        }
    }
}
