package com.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 打包测试
 * @author Administrator
 *
 */
@RunWith(Suite. class )
@Suite.SuiteClasses( {
		JUnit4Test. class ,
        SquareTest. class 
        } )
public class AllTest {

}
