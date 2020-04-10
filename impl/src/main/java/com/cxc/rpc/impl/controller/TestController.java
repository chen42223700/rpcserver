package com.cxc.rpc.impl.controller;

import com.csc.rpc.rpcinterface.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author Link.Chen
 * @Date2020/4/2 15:39
 **/
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private HelloService helloService;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/hello")
    public String hello(){
        HelloService o = (HelloService)applicationContext.getBean(HelloService.class);
        System.out.println(o.sayHello("get bean"));
        return helloService.sayHello("test");
    }

}
