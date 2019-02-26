package com.gf.test01;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Send {

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
            String message = "MSG";

//            Thread work = new Thread(()->{
//                while(true){
//                    try {
//                        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (IOException | InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            work.start();
//            work.join();

            channel.basicPublish("", QUEUE_NAME, null, (message + "1").getBytes());
            channel.basicPublish("", QUEUE_NAME, null, (message + "2").getBytes());
            channel.basicPublish("", QUEUE_NAME, null, (message + "3").getBytes());
            channel.basicPublish("", QUEUE_NAME, null, (message + "4").getBytes());

            System.out.println("[x] Sent '" + message + "'");

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
