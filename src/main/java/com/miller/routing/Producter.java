package com.miller.routing;

import com.miller.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by miller on 2018/7/14
 */
public class Producter {

    public static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //exchange 路由
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String str = "hello direct";
        String routingKey = "info";
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, str.getBytes("utf-8"));

        channel.close();
        connection.close();
    }
}
