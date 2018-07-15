package com.miller.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by miller on 2018/7/14
 */
public class ConnectionUtil {

    private ConnectionUtil() {
    }

    private static ConnectionFactory factory = null;

    static {
        // 定义连接工厂
        factory = new ConnectionFactory();
        // 设置服务地址
        factory.setHost("192.168.163.140");
        // 端口 amqp协议, 类似mysql的3306
        factory.setPort(5672);
        // 设置账号信息,用户名 密码 vhost(相当于数据库)
        factory.setVirtualHost("/vhost_miller");
        factory.setUsername("miller");
        factory.setPassword("miller");
    }

    public static Connection getConnection() throws IOException, TimeoutException {
        return  factory.newConnection();
    }
}
