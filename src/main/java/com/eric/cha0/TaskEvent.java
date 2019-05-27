package com.eric.cha0;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.IOException;
import java.util.Random;

/**
 * @Description 并行任务发生器
 * @Author eric
 * @Version V1.0.0
 * @Date 2019/5/27
 */
public class TaskEvent {

    public static void main(String[] args) {
        ZContext context = new ZContext();
        ZMQ.Socket sender = context.createSocket(SocketType.PUSH);
        sender.bind("tcp://*:5557");
        ZMQ.Socket slink = context.createSocket(SocketType.PUSH);
        slink.connect("tcp://localhost:5558");
        System.out.println("please press enter when the workers are ready:");
        try {
            System.in.read();
            System.out.println("sending tasks to workers");
            slink.send("0", 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Random srandom = new Random(System.currentTimeMillis());
        int task_nbr;
        //总的耗时期望(毫秒)
        int total_msec = 0;
        for (task_nbr = 0; task_nbr < 100; task_nbr++) {
            int workload;
            workload = srandom.nextInt(100) + 1;
            total_msec += workload;
            System.out.print(workload + ".");
            String string = String.format("%d", workload);
            sender.send(string, 0);
        }
        System.out.println("总的耗时统计：" + total_msec + "毫秒");
        //给ZMQ充足的时间去分发消息
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
