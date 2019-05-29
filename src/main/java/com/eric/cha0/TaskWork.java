package com.eric.cha0;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.StringTokenizer;

/**
 * @Description 并行任务工人
 * @Author eric
 * @Version V1.0.0
 * @Date 2019/5/29
 */
public class TaskWork {

    public static void main(String[] args) {
        ZContext context = new ZContext();
        //接收方
        ZMQ.Socket receiver = context.createSocket(SocketType.PULL);
        receiver.connect("tcp://localhost:5557");
        //发送方
        ZMQ.Socket sender = context.createSocket(SocketType.PUSH);
        sender.connect("tcp://localhost:5558");
        //当前线程没有中断
        while (!Thread.currentThread().isInterrupted()){
            String string = new String(receiver.recv(0), ZMQ.CHARSET).trim();
            long mesc = Long.parseLong(string);
            System.out.flush();
            System.out.println(string+'.');
            try {
                Thread.sleep(mesc);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sender.send(ZMQ.MESSAGE_SEPARATOR,0);
        }


    }
}
