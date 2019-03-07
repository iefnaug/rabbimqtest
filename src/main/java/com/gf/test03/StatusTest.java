package com.gf.test03;

import com.gf.utils.RabbitUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class StatusTest {

    public static void main(String[] args) {
        Connection connection = RabbitUtils.getConnection();
        Channel channel = null;
        try {
            channel = connection.createChannel();

            channel.exchangeDeclare("logs-exchange", BuiltinExchangeType.TOPIC, false);

            channel.queueDeclare("msg-inbox-errors", false, false, false, null);
            channel.queueDeclare("msg-inbox-logs", false, false, false, null);
            channel.queueDeclare("all-logs", false, false, false, null);

            channel.queueBind("msg-inbox-errors", "logs-exchange", "error.msg-inbox");
            channel.queueBind("all-logs", "logs-exchange", "*.msg-inbox");


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            RabbitUtils.release(channel, connection);
        }
    }
}
