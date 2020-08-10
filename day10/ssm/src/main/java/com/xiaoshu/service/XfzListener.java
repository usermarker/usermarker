package com.xiaoshu.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class XfzListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		TextMessage person=(TextMessage) message;
		try {
			System.out.println(person.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
