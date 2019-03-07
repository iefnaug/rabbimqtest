package com.gf.test02;

import com.gf.utils.RabbitUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.UUID;

public class NewTask {

    public static void main(String[] args) {
        String msg = "msg-" + UUID.randomUUID().toString();
        Connection connection = RabbitUtils.getConnection();
        Channel channel = null;
        try {
            channel = connection.createChannel();
            channel.exchangeDelete("task_exchange");
            channel.exchangeDeclare("task_exchange", BuiltinExchangeType.DIRECT, false); //声明交换器
            channel.queueDeclare("task_queue", false, false, false, null);//声明队列
            channel.queueBind("task_queue", "task_exchange", "task_msg");//将队列和交换器绑定
            channel.basicPublish("task_exchange", "task_msg",false, null, msg.getBytes());//发送消息到交换器
            channel.basicPublish("task_exchange", "task_msg", null, msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            RabbitUtils.release(channel, connection);
        }
        System.out.println("发送消息结束");
    }
}
