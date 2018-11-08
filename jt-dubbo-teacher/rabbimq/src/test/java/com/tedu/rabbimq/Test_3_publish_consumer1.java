package com.tedu.rabbimq;

import java.util.UUID;

import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class Test_3_publish_consumer1 {

	@Test
	public void consumer() throws Exception {
		// 1.建立连接
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.88.136");
		factory.setPort(5672);
		factory.setUsername("jtadmin");
		factory.setPassword("jtadmin");
		factory.setVirtualHost("/jt");
		// 2.创建通道
		// com.rabbitmq.client.Connection
		Connection connection = factory.newConnection();
		// com.rabbitmq.client
		Channel channel = connection.createChannel();
		//指定交换机
		String exchangeName = "e1";
		channel.exchangeDeclare(exchangeName, "fanout");
		
		// 3.创建队列
		String queueName = UUID.randomUUID().toString();
		System.out.println("订阅 消费者1 队列名称 "+queueName);
		// p1:队列名
		// p2:durable true 保存到硬盘上,重启后还有
		// p3:exclusive true 消息只能由connection消费 false
		// p4:autoDelete true 消息处理完了,删除队列
		// p5:arguments配置
		channel.queueDeclare(queueName, true, false, false, null);
		//把队列和交换机绑定
		channel.queueBind(queueName, exchangeName, "");
		//每次取几个数据
		channel.basicQos(1);
		// 4.创建消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		// 5.消费者和队列绑定
		channel.basicConsume(queueName, true, consumer);
		// 6.多个数据list,用迭代器
		while(true){
			//取数据
			Delivery delivery = consumer.nextDelivery();
			byte[] data = delivery.getBody();
			String string = new String(data);
			System.out.println("消费者1取到: "+string);
		}
	}

}
