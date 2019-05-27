package com.eric.cha0;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

/**
 * @Description zmq服务端
 * @Author eric
 * @Version V1.0.0
 * @Date 2019/5/27
 */
public class HwServer {

    public static void main(String[] args) {

        ZContext context = new ZContext();
        ZMQ.Socket socket = context.createSocket(SocketType.REP);
        socket.bind("tcp://*:5555");
        //当前线程没有被中断
        while (!Thread.currentThread().isInterrupted()){
            byte[] reply = socket.recv(0);
            System.out.println("收到："+new String(reply,ZMQ.CHARSET));
            String response="world";
            socket.send(response.getBytes(ZMQ.CHARSET),0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
