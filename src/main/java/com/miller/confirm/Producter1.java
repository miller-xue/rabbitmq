package com.miller.confirm;

import com.miller.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by miller on 2018/7/15
 * confirm 普通模式
 */
public class Producter1 {
    public static final String QUEUE_NAME = "test_queue_confirm_1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 生产者调用confirmSelect 将channel 设为 confirm模式
        channel.confirmSelect();

        String msg = "hello confirm message";

        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        boolean b = channel.waitForConfirms();
        if (b) {
            System.out.println("发送成功");
        }else {
            System.out.println("发送失败");
        }
        channel.close();
        connection.close();
    }
}
