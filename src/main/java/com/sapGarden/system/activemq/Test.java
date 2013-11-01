package com.sapGarden.system.activemq;

import java.util.Date;

public class Test {

	private  InvokeMessageProducer invokeMessageProducer;
	public void setInvokeMessageProducer(InvokeMessageProducer invokeMessageProducer) {
		this.invokeMessageProducer = invokeMessageProducer;
	}
	public void sendMsg(){
		try{  
		    for(int i=0;i<1;i++){  
		        //准备发送jms消息  
		        InvokeMessage im = new  InvokeMessage();  
		        im.setMsg(i+":有人查询用户列表了！["+new Date()+"]");  
		        im.setName("当前系统通知");  
		        im.setOperate("查询！");  
		        this.invokeMessageProducer.send(im);  
		    }  
		  
		}catch(Exception e){  
		    System.out.println("发送系统消息出错");  
		    e.printStackTrace();  
		}  
	}

}
