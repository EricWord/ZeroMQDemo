package com.eric.cha2;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import javax.naming.ldap.SortKey;

/**
 * @Description 多线程服务
 * @Author eric
 * @Version V1.0.0
 * @Date 2019/6/3
 */
public class MTServer {

    private static class Worker extends Thread {
        private ZContext context;

        private Worker(ZContext context) {
            this.context = context;

        }


        @Override
        public void run() {
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            socket.connect("inproc://workers");
            while (true) {
                //等待客户端的下一个连接
                String request = socket.recvStr(0);
                System.out.println(Thread.currentThread().getName() + "接收到请求：" + request);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                socket.send("world", 0);

            }
        }
    }

    public static void main(String[] args) {
        try(ZContext context=new ZContext()){
            ZMQ.Socket clients = context.createSocket(SocketType.ROUTER);
            clients.bind("tcp://*:5555");
            ZMQ.Socket worker = context.createSocket(SocketType.DEALER);



        }

    }


}
