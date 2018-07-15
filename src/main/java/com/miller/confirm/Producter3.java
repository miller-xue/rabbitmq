package com.miller.confirm;

import com.miller.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * Created by miller on 2018/7/15
 * confirm 异步回调
 */
public class Producter3 {
    public static final String QUEUE_NAME = "test_queue_confirm_3";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 生产者调用confirmSelect 将channel 设置位confirm 模式 注意,不能跟事务共存
        channel.confirmSelect();

        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        // 通道添加监听
        channel.addConfirmListener(new ConfirmListener() {
            // 没问题的handleACK
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("handleAck----------multiple");
                    confirmSet.headSet(deliveryTag + 1).clear();
                } else {
                    System.out.println("handleAck----------multiple  false");
                    confirmSet.remove(deliveryTag);
                }

            }

            // 有问题的
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("handleNack----------multiple");
                    confirmSet.headSet(deliveryTag + 1).clear();
                } else {
                    System.out.println("handleNack----------multiple  false");
                    confirmSet.remove(deliveryTag);
                }
            }
        });

        String msg = "ssssss";

        while (true) {
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            confirmSet.add(seqNo);
        }
    }

}
