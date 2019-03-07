package com.gf.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitUtils {

    private static ConnectionFactory connectionFactory;

    static {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.61.135");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("gf");
        connectionFactory.setPassword("test");
        connectionFactory.setVirtualHost("test");
    }

    public static Connection getConnection(){
        try {
            return connectionFactory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("cannot create rabbit connection");
    }

    public static void release(AutoCloseable ...resources){
        for(AutoCloseable closeable : resources){
            try {
                if(closeable != null){
                    closeable.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
