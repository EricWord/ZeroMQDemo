package com.eric.cha0;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.StringTokenizer;

/**
 * @Description 天气客户端
 * @Author eric
 * @Version V1.0.0
 * @Date 2019/5/27
 */
public class WUClient {

    public static void main(String[] args) {
        ZContext context = new ZContext();
        System.out.println("开始获取来自天气服务器的更新信息..");
        ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
        subscriber.connect("tcp://localhost:5556");
        String filter = (args.length > 0) ? args[0] : "10001 ";
        subscriber.subscribe(filter.getBytes(ZMQ.CHARSET));
        //执行100次更新
        int update_nbr;
        long total_temp = 0;
        for (update_nbr = 0; update_nbr < 100; update_nbr++) {
            //去掉空格
            String string = subscriber.recvStr(0).trim();
            StringTokenizer sscanf = new StringTokenizer(string, " ");
            //获取来自服务器的邮编
            int zipCode = Integer.valueOf(sscanf.nextToken());
            //温度
            int tempature = Integer.valueOf(sscanf.nextToken());

            //湿度
            int relhumidity = Integer.valueOf(sscanf.nextToken());
            //总的温度
            total_temp += tempature;
        }
        System.out.println(String.format("Average tempature for zipCode '%s' was '%d.'", filter, (int) (total_temp / update_nbr)));

    }


}
