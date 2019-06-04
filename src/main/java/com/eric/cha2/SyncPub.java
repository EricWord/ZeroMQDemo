package com.eric.cha2;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

/**
 * @Description 同步的发布者
 * @Author eric
 * @Version V1.0.0
 * @Date 2019/6/4
 */
public class SyncPub {

    //等待10个订阅者
    protected static int SUBSCRIBERS_EXPECTED = 10;

    public static void main(String[] args) {
        //创建上下文对象
        try (ZContext context = new ZContext()) {
            //  与客户端进行通信的Socket
            ZMQ.Socket publisher = context.createSocket(SocketType.PUB);
            publisher.setLinger(5000);
            // In 0MQ 3.x pub socket could drop messages if sub can follow the
            // generation of pub messages
            publisher.setSndHWM(0);
            publisher.bind("tcp://*:5561");

            // 用于接收信号的socket
            ZMQ.Socket syncservice = context.createSocket(SocketType.REP);
            syncservice.bind("tcp://*:5562");

            System.out.println("Waiting for subscribers");
            //  同步订阅者
            int subscribers = 0;
            while (subscribers < SUBSCRIBERS_EXPECTED) {
                // 等待同步请求
                syncservice.recv(0);

                //  发送同步响应
                syncservice.send("", 0);
                subscribers++;
            }
            //  Now broadcast exactly 1M updates followed by END
            System.out.println("Broadcasting messages");

            int update_nbr;
            for (update_nbr = 0; update_nbr < 1000000; update_nbr++) {
                publisher.send("Rhubarb", 0);
            }

            publisher.send("END", 0);
        }
    }
}
