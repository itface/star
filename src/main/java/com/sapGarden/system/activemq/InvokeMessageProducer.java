package com.sapGarden.system.activemq;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;

public class InvokeMessageProducer {

	private JmsTemplate template;  
	  
    private Queue destination;  
  
    public void setTemplate(JmsTemplate template) {  
        this.template = template;  
    }  
  
    public void setDestination(Queue destination) {  
        this.destination = destination;  
    }  
  
    public void send(InvokeMessage invokeMessage) {  
    	template.convertAndSend(this.destination, invokeMessage);  
    	ActiveMQQueue msgQueue = new ActiveMQQueue();
		msgQueue.setPhysicalName("hellomq");
		template.convertAndSend(msgQueue,"hello mqwolrd,i'm coming!!!");
    }  
}
