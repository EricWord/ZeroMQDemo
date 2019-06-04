package com.eric.cha2;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

/**
 * @Description 发布-订阅封包订阅者
 * @Author eric
 * @Version V1.0.0
 * @Date 2019/6/4
 */
public class PSenvSub {

    public static void main(String[] args) {
        // 准备上下文和发布者
        try (ZContext context = new ZContext()) {
            ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
            subscriber.connect("tcp://localhost:5563");
            subscriber.subscribe("B".getBytes(ZMQ.CHARSET));

            while (!Thread.currentThread().isInterrupted()) {
                // 读取封包的地址
                String address = subscriber.recvStr();
                // 读取消息内容
                String contents = subscriber.recvStr();
                System.out.println(address + " : " + contents);
            }
        }
    }
}
