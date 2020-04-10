package com.cxc.rpc.impl.service;


import com.csc.rpc.rpcinterface.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Link.Chen
 * @Date2020/4/2 13:50
 **/
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

    @Override
    public String sayHelloWithOutArgs() {
        return "hello";
    }
}
