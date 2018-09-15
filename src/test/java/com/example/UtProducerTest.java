package com.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by henry on 2018/9/15.
 */
@Slf4j
public class UtProducerTest {
    private static final String RABBITMQ_EXCHANGE_KEY = "hello-exchange";

    private ConnectionFactory factory;

    @Before
    public void before() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
    }

    @Test
    public void testTaskProducer() throws IOException, TimeoutException, InterruptedException {
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(RABBITMQ_EXCHANGE_KEY, "direct", true);

        for (int i = 0; i < 10; i++) {
            channel.basicPublish(RABBITMQ_EXCHANGE_KEY, "task", null, String.format("task..%d", i).getBytes());
            Thread.sleep(1000L);
        }

        channel.close();
        conn.close();
    }

    @Test
    public void testUserProducer() throws IOException, TimeoutException, InterruptedException {
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(RABBITMQ_EXCHANGE_KEY, "direct", true);

        for (int i = 0; i < 10; i++) {
            channel.basicPublish(RABBITMQ_EXCHANGE_KEY, "user", null, String.format("user..%d", i % 3).getBytes());
            Thread.sleep(2000L);
        }

        channel.close();
        conn.close();
    }
}
