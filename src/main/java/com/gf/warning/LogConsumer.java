package com.gf.warning;

import com.gf.utils.RabbitUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LogConsumer {

    public static void main(String[] args) throws IOException {

        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();

        //topic 交换器
        channel.exchangeDeclare("alters", BuiltinExchangeType.TOPIC, false, false, null);
        //创建队列critical
        channel.queueDeclare("critical", false, false, false, null);
        channel.queueBind("critical", "alters", "critical.*");
        //创建队列rate_limit
        channel.queueDeclare("rate_limit", false, false, false, null);
        channel.queueBind("rate_limit", "alters", "*.rate_limit");


        channel.basicConsume("critical", false, (consumerTag, message) -> {
            String msg = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println("critical队列消息====> " + msg);
            System.out.println("consumerTag:" + consumerTag + "  deliveryTag:" + message.getEnvelope().getDeliveryTag());
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        }, consumerTag -> {});

        channel.basicConsume("rate_limit", false, (consumerTag, message) -> {
            String msg = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println("rate_limit队列消息====> " + msg);
            System.out.println("consumerTag:" + consumerTag + "  deliveryTag:" + message.getEnvelope().getDeliveryTag());
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        }, consumerTag -> {});
    }
}
