package com.test;

import junit.framework.JUnit4TestAdapter;

import org.junit.runner.JUnitCore;

public class Test {

	public static void main(String[] args) {   
        JUnitCore.runClasses(JUnit4Test.class);      
        //Test.suite();   
    }   
  
    public static junit.framework.Test suite() {    
        return new JUnit4TestAdapter(JUnit4Test.class);    
    }   
}
