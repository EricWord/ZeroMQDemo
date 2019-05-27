package com.eric.cha0;

import org.zeromq.ZMQ;

/**
 * @Description ZMQ版本
 * @Author eric
 * @Version V1.0.0
 * @Date 2019/5/27
 */
public class ZMQVersion {

    public static void main(String[] args) {
        String versioin = ZMQ.getVersionString();
        int fullVersion = ZMQ.getFullVersion();
        System.out.println(String.format("Version string %s,Version int: %d",versioin,fullVersion));
    }
}
