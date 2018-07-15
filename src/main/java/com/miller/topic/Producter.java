package com.miller.topic;

import com.miller.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by miller on 2018/7/14
 */
public class Producter {
    public static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();


        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        channel.basicQos(1);

        String msg = "商品。。。。。。。。";

        channel.basicPublish(EXCHANGE_NAME, "goods.delete", null, msg.getBytes("utf-8"));


        System.out.println("send ----" + msg);

        channel.close();
        connection.close();
    }
}
