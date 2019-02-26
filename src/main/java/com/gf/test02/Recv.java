package com.gf.test02;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("***Waiting for message");

        DeliverCallback callback = (consumerTag, message) -> {
            String result = new String(message.getBody(), "utf-8");
            System.out.println("Recieved: " +  result);
        };
        channel.basicConsume(QUEUE_NAME, true, callback, consumerTag -> {});
    }
}
