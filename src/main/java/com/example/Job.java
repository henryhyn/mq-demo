package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 基于消息队列的任务分配系统演示
 * 演示方法:
 * 1. 按下面第一篇参考文献安装 rabbitmq
 * 2. 执行 UtProducerTest 全部测试
 * 3. 执行 Job main 方法
 * 参考文献:
 * - [消息队列之 RabbitMQ](https://www.jianshu.com/p/79ca08116d57)
 * - [BlockingQueue（阻塞队列）详解](https://www.cnblogs.com/tjudzj/p/4454490.html)
 */
@SpringBootApplication
public class Job {
    public static void main(String[] args) {
        SpringApplication.run(Job.class, args);
    }
}
