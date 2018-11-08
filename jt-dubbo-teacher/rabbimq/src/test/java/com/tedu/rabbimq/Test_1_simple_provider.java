package com.tedu.rabbimq;

import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Test_1_simple_provider {
	
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
		//3.创建队列
		String queueName = "order";
		//p1:队列名
		//p2:durable true	保存到硬盘上,重启后还有
		//p3:exclusive true 消息只能由connection消费 false
		//p4:autoDelete true 消息处理完了,删除队列
		//p5:arguments配置	
		channel.queueDeclare
		(queueName, true, false, false, null);
		//4.发消息
		//p1:是交换机,给"",发到default exchange
		channel.basicPublish("", queueName, null, "msg1".getBytes());
		//5.关闭
		//channel.close();
		//connection.close();
		while(true){}
		//在后台管理中查看connections
		//在后台管理overview,可以看到队列中消息的个数
	}
	
}
