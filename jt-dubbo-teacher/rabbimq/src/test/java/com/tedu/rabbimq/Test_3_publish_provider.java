package com.tedu.rabbimq;

import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Test_3_publish_provider {
	
	@Test
	public void provider() throws Exception{
		//1.建立连接
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.88.136");
		factory.setPort(5672);
		factory.setUsername("jtadmin");
		factory.setPassword("jtadmin");
		factory.setVirtualHost("/jt");
		//2.创建通道
		//com.rabbitmq.client.Connection
		Connection connection = factory.newConnection();
		//com.rabbitmq.client
		Channel channel = connection.createChannel();
		String exchangeName = "e1";
		//创建交换机
		//p2:type
		//fanout 订阅模式
		//direct 路由模式
		//topic	 主题模式
		channel.exchangeDeclare(exchangeName, "fanout");
		channel.basicPublish(exchangeName, "", null, "msg1".getBytes());
		channel.close();
		connection.close();
		//订阅,路由,主题,数据发成功后,后台看不到数量
	}
	
}
