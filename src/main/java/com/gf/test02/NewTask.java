package com.gf.test02;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class NewTask {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);

        try(
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()
                ){
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            Thread work = new Thread(()->{
                int index = 0;
                while(true){
                    String message = "instruction";
                    message = (index + "." + message);
                    try {
                        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                        TimeUnit.SECONDS.sleep(1);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    index++;
                    System.out.println("[x] Sent '" + message + "'");
                }

            });
            work.start();
            work.join();

        } catch (TimeoutException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
