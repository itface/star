package com.sapGarden.system.activemq;

import javax.jms.Message;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
//@Component("consumerOfManual")
public class ConsumerOfManual implements MqService{

	//@Autowired
	private JmsTemplate template;  
	  
    public void setTemplate(JmsTemplate template) {  
        this.template = template;  
    }  
    @Transactional
    public void send() {  
    	//template.setSessionTransacted(true);
    	//template.setSessionAcknowledgeMode(0);
    	ActiveMQQueue msgQueue = new ActiveMQQueue();
		msgQueue.setPhysicalName("hellomq");
		Message message = template.receive(msgQueue);
		//System.out.println(template.receive(msgQueue));
		//Object obj = template.receiveAndConvert(msgQueue);
		System.out.println("11111111");
		//throw new RuntimeException();
    }  
}
