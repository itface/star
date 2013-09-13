package com.itface.star.system.util.guava;

import java.util.concurrent.ConcurrentMap;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;

/**
 * MapMaker 是用来构造 ConcurrentMap 的工具类。
 * @author Administrator
 *
 */
public class MyMapMaker {

	public void mapmaker1(){
		 //ConcurrentHashMap with concurrency level 8 
		 ConcurrentMap<String, Object> map1 = new MapMaker() 
		    .concurrencyLevel(8) 
		     .makeMap(); 
	}
	/**
	 * 构造用各种不同 reference 作为 key 和 value 的 Map:
	 */
	public void mapmaker2(){
		 //ConcurrentMap with soft reference key and weak reference value 
		 ConcurrentMap<String, Object> map2 = new MapMaker().weakKeys() 
		    .weakValues() 
		    .makeMap(); 
	}
	public void mapmaker3(){
		//Automatically removed entries from map after 30 seconds since they are created 
		ConcurrentMap<String, Integer> cache = new MapMaker()
	    .makeComputingMap(new Function<String, Integer>() {
	      @Override public Integer apply(String name) {
	        return 1000;
	      }
	    });
	}
}
