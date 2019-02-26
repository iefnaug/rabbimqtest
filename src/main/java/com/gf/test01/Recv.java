package com.gf.test01;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
//            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume(QUEUE_NAME, false, callback, consumerTag -> {});

//        GetResponse response = channel.basicGet(QUEUE_NAME, false);
//        System.out.println(new String(response.getBody(), StandardCharsets.UTF_8));
    }
}
