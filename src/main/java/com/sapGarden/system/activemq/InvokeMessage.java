package com.sapGarden.system.activemq;

import java.io.Serializable;

public class InvokeMessage implements Serializable{  
      
    private static final long serialVersionUID = 2L;  
  
    private String name;  
      
    private String operate;  
      
    private String msg;  
  
    public String getMsg() {  
        return msg;  
    }  
  
    public void setMsg(String msg) {  
        this.msg = msg;  
    }  
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public String getOperate() {  
        return operate;  
    }  
  
    public void setOperate(String operate) {  
        this.operate = operate;  
    }  

}
