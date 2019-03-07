package com.gf.test02;

import com.gf.utils.RabbitUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class Worker2 {

    public static void main(String[] args) {
        System.out.println("消费者线程启动...");
        Connection connection = RabbitUtils.getConnection();
        Channel channel = null;
        try{
            channel = connection.createChannel();
            channel.exchangeDeclare("task_exchange", BuiltinExchangeType.DIRECT, false); //声明交换器
            channel.queueDeclare("task_queue2", false, false, false, null);//声明队列
            channel.queueBind("task_queue2", "task_exchange", "task_msg");//将队列和交换器绑定

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("接收消息: " + message + "  ,开始执行任务...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务执行结束..." + message);
            };

            boolean autoAck = true;
            channel.basicConsume("task_queue2", autoAck, deliverCallback, consumerTag -> {});
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            RabbitUtils.release(channel, connection);
        }
    }
}
