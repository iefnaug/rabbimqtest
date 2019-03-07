package com.gf.warning;

import com.gf.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LogProducer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();

//        //topic 交换器
//        channel.exchangeDeclare("alters", BuiltinExchangeType.TOPIC, false, false, null);
//        //创建队列critical
//        channel.queueDeclare("critical", false, false, false, null);
//        channel.queueBind("critical", "alters", "critical.*");
//        //创建队列rate_limit
//        channel.queueDeclare("rate_limit", false, false, false, null);
//        channel.queueBind("rate_limit", "alters", "*.rate_limit");

        String[] keys = {
                "critical.msg",
                "msg.rate_limit",
                "critical.rate_limit"
        };
        String[] messages = {
            "one", "two", "three"
        };
        Random random = new Random();

        while(true){
            int index = random.nextInt(3);
            channel.basicPublish("alters", keys[index], null, (messages[index]).getBytes());
            TimeUnit.SECONDS.sleep(2);
        }
    }
}
