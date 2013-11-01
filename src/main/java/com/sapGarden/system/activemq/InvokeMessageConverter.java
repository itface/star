package com.sapGarden.system.activemq;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

public class InvokeMessageConverter implements MessageConverter{  
	public Message toMessage(Object object, Session session)throws JMSException, MessageConversionException {
		return session.createObjectMessage((Serializable) object);
	}

	public Object fromMessage(Message message) throws JMSException,MessageConversionException {
		ObjectMessage objMessage = (ObjectMessage) message;
		return objMessage.getObject();
	}
    //接收消息时候使用  
//    public Object fromMessage(Message msg) throws JMSException, MessageConversionException {  
//        if (msg instanceof ObjectMessage) {  
//            if (msg instanceof ObjectMessage) {   
//            	Object obj = ((ObjectMessage) msg).getObject();
//            	return obj;
//            	/*
//                HashMap<String, byte[]> map = (HashMap<String, byte[]>) ((ObjectMessage) msg).getObjectProperty("Map");      
//                try {      
//                    // POJO must implements Seralizable      
//                    ByteArrayInputStream bis = new ByteArrayInputStream(map.get("InvokeMessage"));      
//                    ObjectInputStream ois = new ObjectInputStream(bis);      
//                    Object returnObject = ois.readObject();      
//                    return returnObject;      
//                } catch (IOException e) {      
//                    System.out.println("fromMessage(Message)");  
//                    e.printStackTrace();  
//         
//                } catch (ClassNotFoundException e) {  
//                     System.out.println("fromMessage(Message)");  
//                     e.printStackTrace();  
//                }  */
//            }  
//              
//        } else {  
//            throw new JMSException("Msg:[" + msg + "] is not Map");  
//        }  
//        return null;  
//    }  
//   //发送消息时候使用  
//    public Message toMessage(Object obj, Session session) throws JMSException, MessageConversionException {  
//  
//        if (obj instanceof InvokeMessage) {  
//            ActiveMQObjectMessage objMsg = (ActiveMQObjectMessage) session.createObjectMessage();  
//            Map<String, byte[]> map = new HashMap<String, byte[]>();  
//            try {  
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();      
//                ObjectOutputStream oos = new ObjectOutputStream(bos);  
//                oos.writeObject(obj);  
//                map.put("InvokeMessage", bos.toByteArray());  
//            } catch (IOException e) {  
//                e.printStackTrace();  
//            }      
//            objMsg.setObjectProperty("Map", map);  
//            return objMsg;  
//        } else {  
//            throw new JMSException("Object:[" + obj + "] is not InvokeMessage");  
//        }  
//    }   

}
