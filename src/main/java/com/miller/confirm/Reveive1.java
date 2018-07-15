package com.miller.confirm;

import com.miller.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by miller on 2018/7/15
 */
public class Reveive1 {
    public static final String QUEUE_NAME = "test_queue_confirm_2";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("receive confirm msg = " + new String(body));
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
