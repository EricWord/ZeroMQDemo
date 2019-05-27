package com.eric.cha0;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

/**
 * @Description ZMQ客户端
 * @Author eric
 * @Version V1.0.0
 * @Date 2019/5/27
 */
public class HWClient {

    public static void main(String[] args) {
        ZContext context = new ZContext();
        System.out.println("连接到服务器...");
        ZMQ.Socket socket = context.createSocket(SocketType.REQ);
        socket.connect("tcp://localhost:5555");
        for (int resquestNbr = 0; resquestNbr != 10; resquestNbr++) {
            String resquest = "hello";
            System.out.println("发送 hello" + resquestNbr);
            socket.send(resquest.getBytes(ZMQ.CHARSET), 0);
            byte[] reply = socket.recv(0);
            System.out.println("收到 " + new String(reply, ZMQ.CHARSET) + " " + resquestNbr);
        }

    }

}
