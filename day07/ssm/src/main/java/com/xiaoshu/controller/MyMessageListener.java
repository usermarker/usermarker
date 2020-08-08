package com.xiaoshu.controller;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;
import com.xiaoshu.entity.ExpressPerson;

public class MyMessageListener implements MessageListener {

	@Autowired
	private RedisTemplate redisTemplate;
	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			String json = textMessage.getText();
			//将数据存入redis中
			redisTemplate.boundHashOps("expressPerson").put("person", json);
			System.out.println("将数据存入redis中");
			ExpressPerson person = JSON.parseObject(json, ExpressPerson.class);
			System.out.println("MQ接收到的数据为="+person);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
