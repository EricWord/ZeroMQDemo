package com.eric.cha2;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

/**
 * @Description 同步的订阅者
 * @Author eric
 * @Version V1.0.0
 * @Date 2019/6/4
 */
public class SyncSub {

    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            //  首先连接订阅者套接字
            ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
            subscriber.connect("tcp://localhost:5561");
            subscriber.subscribe(ZMQ.SUBSCRIPTION_ALL);

            // 其次，与发布者同步
            ZMQ.Socket syncclient = context.createSocket(SocketType.REQ);
            syncclient.connect("tcp://localhost:5562");

            //  发送同步请求
            syncclient.send(ZMQ.MESSAGE_SEPARATOR, 0);

            //等待同步应答
            syncclient.recv(0);

            //  获取更新，并报告收到的更新数量
            int update_nbr = 0;
            while (true) {
                String string = subscriber.recvStr(0);
                if (string.equals("END")) {
                    break;
                }
                update_nbr++;
            }
            System.out.println("Received " + update_nbr + " updates.");
        }
    }
}
