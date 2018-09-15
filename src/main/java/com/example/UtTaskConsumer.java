package com.example;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by henry on 2018/9/15.
 */
@Slf4j
@Component
public class UtTaskConsumer implements InitializingBean {
    private static final String RABBITMQ_QUEUE_NAME = "allocate-task";
    private static final String RABBITMQ_EXCHANGE_KEY = "task-exchange";
    private static final String RABBITMQ_ROUTING_KEY = "task";
    private BlockingQueue blockingQueue;

    public UtTaskConsumer() {
        blockingQueue = new ArrayBlockingQueue(2);
    }

    public Object getTask() throws InterruptedException {
        return blockingQueue.take();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.queueDeclare(RABBITMQ_QUEUE_NAME, true, false, false, null);
        channel.queueBind(RABBITMQ_QUEUE_NAME, RABBITMQ_EXCHANGE_KEY, RABBITMQ_ROUTING_KEY);
        log.info("UtTaskConsumer ready.");
        channel.basicConsume(RABBITMQ_QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String bodyStr = new String(body, "UTF-8");
                log.info("加入 {}", bodyStr);
                try {
                    blockingQueue.put(bodyStr);
                } catch (InterruptedException e) {
                    log.error("加入 {} 失败", bodyStr, e);
                }
            }
        });
    }
}
