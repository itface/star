package com.test;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class DBUnitTestExecutionListener implements TestExecutionListener{

	public void prepareTestInstance(TestContext testContext) throws Exception {  
    }  
  
    public void beforeTestClass(TestContext testContext) throws Exception {  
        // Nothing to do  
    }  
  
    public void afterTestClass(TestContext testContext) throws Exception {  
        // Nothing to do  
    }  
  
    public void beforeTestMethod(TestContext testContext) throws Exception {  
        
    }  
  
    public void afterTestMethod(TestContext testContext) throws Exception {  
        // Nothing to do  
    }  
  

}
