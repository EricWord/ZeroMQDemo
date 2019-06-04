package com.eric.cha2;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

/**
 * @Description 发布-订阅封包发布者
 * @Author eric
 * @Version V1.0.0
 * @Date 2019/6/4
 */
public class PsenvPub {
    public static void main(String[] args) throws Exception {
        // 准备上下文和发布者
        try (ZContext context = new ZContext()) {
            ZMQ.Socket publisher = context.createSocket(SocketType.PUB);
            publisher.bind("tcp://*:5563");

            while (!Thread.currentThread().isInterrupted()) {
                // 编写两个消息，每个消息都带有一个封包和内容
                publisher.sendMore("A");
                publisher.send("We don't want to see this");
                publisher.sendMore("B");
                publisher.send("We would like to see this");
            }
        }
    }
}
