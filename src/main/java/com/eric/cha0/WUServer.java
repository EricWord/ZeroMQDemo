package com.eric.cha0;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Random;

/**
 * @Description 天气更新服务端程序
 * @Author eric
 * @Version V1.0.0
 * @Date 2019/5/27
 */
public class WUServer {

    public static void main(String[] args) {
        ZContext context = new ZContext();
        ZMQ.Socket publisher = context.createSocket(SocketType.PUB);
        publisher.bind("tcp://*:5556");
        publisher.bind("ipc://weather");
        Random srandom = new Random(System.currentTimeMillis());
        //当前线程没有被打断
        while (!Thread.currentThread().isInterrupted()) {
            //邮编、温度、湿度
            int zipCode, tempature, relhumidity;
            zipCode = 10000 + srandom.nextInt(10000);
            tempature = srandom.nextInt(215) - 80 + 1;
            relhumidity = srandom.nextInt(50) + 10 + 1;
            String update = String.format("%05d %d %d", zipCode, tempature, relhumidity);
            publisher.send(update, 0);


        }

    }
}
