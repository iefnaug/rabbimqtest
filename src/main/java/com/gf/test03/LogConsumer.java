package com.gf.test03;

import com.gf.utils.RabbitUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LogConsumer {

    public static void main(String[] args) throws IOException {


        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();

        AMQP.Queue.DeclareOk queue = channel.queueDeclare();
        System.out.println(queue.getQueue());
        channel.queueBind(queue.getQueue(), "amq.topic", "info");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println(msg);
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };

        channel.basicConsume(queue.getQueue(), false, deliverCallback, consumerTag -> {
            System.out.println("队列销毁:" + consumerTag);
        });

    }
}
