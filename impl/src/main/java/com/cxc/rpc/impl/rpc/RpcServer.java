package com.cxc.rpc.impl.rpc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @Author Link.Chen
 * @Date2020/4/2 15:21
 **/
@Component
public class RpcServer implements ApplicationRunner{
    //创建一个线程池
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    @Value("${server.port}")
    private int port;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ServerSocket serverSocket=null;
        try{
            serverSocket=new ServerSocket(8082);  //启动一个服务监听

            while(true){ //循环监听
                Socket socket=serverSocket.accept(); //监听服务
                //通过线程池去处理请求
                executorService.execute(new ProcessorHandler(socket, applicationContext));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
