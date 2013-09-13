package com.test.spring;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class LocationInfoTest extends JUnitControllerBase{

	@Test  
    public void testKeepAlive() {  
        Map<String, String> paramMap = new HashMap<String, String>();  
        paramMap.put("ACTION", "KEEP_ALIVE");  
        ModelAndView model = this.excuteAction("/main/left", 8080, paramMap);  
        System.out.println(model.getViewName());
    }  
  
    /** 
     * TODO: write description for this method. 
     */  
//    @Test  
//    public void testLocationInfo() {  
//        this.excuteAction("/IBEFacade", 8080, new Object[]{"ACTION", "LOCATION_INFO"});  
//    }  
}
