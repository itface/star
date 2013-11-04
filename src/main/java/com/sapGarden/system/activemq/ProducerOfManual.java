package com.sapGarden.system.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
//@Component("producerOfManual")
public class ProducerOfManual {

	//@Autowired
	private JmsTemplate template;  
  
    public void setTemplate(JmsTemplate template) {  
        this.template = template;  
    }  
    public void send() {  
    	//template.setSessionTransacted(true);
    	//template.setSessionAcknowledgeMode(0);
    	ActiveMQQueue msgQueue = new ActiveMQQueue();
		msgQueue.setPhysicalName("hellomq");
		template.convertAndSend(msgQueue,"hello mqwolrd,i'm coming!!!");
    }  
}
