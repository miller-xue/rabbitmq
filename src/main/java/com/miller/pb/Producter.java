package com.miller.pb;

import com.miller.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by miller on 2018/7/14
 * 生产者
 */
public class Producter {
    public static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        String msg = "订阅模式";

        // 发送一天消息
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes("utf-8"));

        System.out.println("send : " + msg);

        channel.close();
        connection.close();
    }
}
