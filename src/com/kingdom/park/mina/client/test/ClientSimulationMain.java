package com.kingdom.park.mina.client.test;

import com.kingdom.park.mina.client.ParkClient;

/**
 * 单客户端测试
 *
 */
public class ClientSimulationMain {


    public static void main(String[] args) {

        ParkClient client = new ParkClient();
        client.pack();
        client.initClient("127.0.0.1:9697","a47175457d23ad011dca079488f052ce",true);//注册windows服务需要
//        client.setSize(800, 500);
//        client.setVisible(true);
    }
}
