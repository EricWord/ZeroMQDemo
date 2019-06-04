package com.eric.cha0;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.StringTokenizer;

/**
 * @Description 并行任务接收器
 * @Author eric
 * @Version V1.0.0
 * @Date 2019/5/29
 */
public class TaskLink {
    /**
     * 将PULL套接字绑定到tcp://localhost:5558
     * 通过套接字收集来自各个工人的结果
     *
     * @param args
     */
    public static void main(String[] args) {
        ZContext context = new ZContext();
        ZMQ.Socket receiver = context.createSocket(SocketType.PULL);
        receiver.bind("tcp://*:5558");
        String string = new String(receiver.recv(0), ZMQ.CHARSET);
        long start = System.currentTimeMillis();
        int task_nbr;
        int total_mesc = 0;
        for (task_nbr = 0; task_nbr < 100; task_nbr++) {
            string = new String(receiver.recv(0), ZMQ.CHARSET);
            if ((task_nbr / 10) * 10 == task_nbr) {
                System.out.println(":");
            } else {
                System.out.println(".");
            }

        }

        long end = System.currentTimeMillis();
        System.out.println("总耗时：" + (end - start) + "毫秒");


    }
}
