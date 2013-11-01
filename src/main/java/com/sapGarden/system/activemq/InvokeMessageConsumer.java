package com.sapGarden.system.activemq;

public class InvokeMessageConsumer {
	public void printMyOut(InvokeMessage invokeMessage) {  
        System.out.println("等待5秒再处理");  
        try {  
            Thread.sleep(5000);  
        } catch (InterruptedException e) {  
  
            e.printStackTrace();  
        }  
        System.out.println("执行业务操作["+invokeMessage.getName()+"],["+invokeMessage.getOperate()+"],["+invokeMessage.getMsg()+"]");  
  
    }  
}
