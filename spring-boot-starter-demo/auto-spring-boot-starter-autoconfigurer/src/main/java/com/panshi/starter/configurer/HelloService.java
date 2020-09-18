package com.panshi.starter.configurer;

/***
 * @Auther: guo
 * @Date: 21:50 2020/9/15
 */
public class HelloService {
    HelloProperties helloProperties;

    public void setHelloProperties(HelloProperties helloProperties) {
        this.helloProperties = helloProperties;
    }

    public String sayHellSuperbeyone(String name){
        return helloProperties.getPrefix()+"-" +name + helloProperties.getSuffix();
    }
}
