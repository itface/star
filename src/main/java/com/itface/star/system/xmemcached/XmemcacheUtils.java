package com.itface.star.system.xmemcached;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

public class XmemcacheUtils {
private static final int defaultWeight = 0;													//	默认权重
	
	private MemcachedClient client ;
	
	public MemcachedClient getClient() {
		return client;
	}

	public void setClient(MemcachedClient client) {
		this.client = client;
	}

	private static final Map<String, String> keyMap = new HashMap<String, String>();			//	可存储的key值
	
	static {
		//Field[] fields = CacheConstant.class.getDeclaredFields();
		Field[] fields = null;
		if(null != fields && fields.length > 0){
			for(Field field : fields){
				keyMap.put(field.getName(), null);
			}
		}			
	}
	
	/**
	 * 校验状态
	 * 
	 * @return
	 */
	private boolean checkClientStatus(){
		boolean flag = false;
		if(null != client && !client.isShutdown()){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 校验是否存在key值
	 * 
	 * @param key
	 * @return
	 */
	private boolean checkKey(String key){
		return keyMap.containsKey(key);
	}
	
	/**
	 * flushAll 清空所有,请谨慎使用
	 * 
	 */
	public void flushAll(){
		if(checkClientStatus()){
			try {
				client.flushAll();
			} catch (TimeoutException e) {
				//logger.error("TimeoutException flushAll :".concat(e.getMessage()));
			} catch (InterruptedException e) {
				//logger.error("InterruptedException flushAll :".concat(e.getMessage()));
			} catch (MemcachedException e) {
				//logger.error("MemcachedException flushAll :".concat(e.getMessage()));
			}
		}
	}
	
	/**
	 * 删除某个key
	 * 
	 * @param key
	 * @return
	 */
	public boolean deleteKey(String key){
		boolean flag = false;
		if(checkClientStatus() && checkKey(key)){
			try {
				flag = client.delete(key);
			} catch (TimeoutException e) {
				//logger.error("TimeoutException deleteKey key=".concat(key).concat(" :").concat(e.getMessage()));
			} catch (InterruptedException e) {
				//logger.error("InterruptedException deleteKey key=".concat(key).concat(" :").concat(e.getMessage()));
			} catch (MemcachedException e) {
				//logger.error("MemcachedException deleteKey key=".concat(key).concat(" :").concat(e.getMessage()));
			}
		}
		return flag;
	}
	
	/**
	 * 存储值
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	public boolean setVal(String key , Object val){
		boolean flag = false;
		if(checkClientStatus() && checkKey(key) && null != val){
			try {
				Object obj = client.get(key);
				if(null == obj){
					flag = client.set(key, defaultWeight, val);
				}else{
					flag = client.replace(key, defaultWeight, val);
				}
			} catch (TimeoutException e) {
				//logger.error("TimeoutException setVal key=".concat(key).concat(" :").concat(e.getMessage()));
			} catch (InterruptedException e) {
				//logger.error("InterruptedException setVal key=".concat(key).concat(" :").concat(e.getMessage()));
			} catch (MemcachedException e) {
				//logger.error("MemcachedException setVal key=".concat(key).concat(" :").concat(e.getMessage()));
			}
		}
		return flag;
	}
	
	/**
	 * 获取值
	 * 
	 * @param key
	 * @return
	 */
	public Object getVal(String key){
		if(checkClientStatus() && checkKey(key)){
			try {
				return client.get(key);
			} catch (TimeoutException e) {
				//logger.error("TimeoutException getVal key=".concat(key).concat(" :").concat(e.getMessage()));
			} catch (InterruptedException e) {
				//logger.error("InterruptedException getVal key=".concat(key).concat(" :").concat(e.getMessage()));
			} catch (MemcachedException e) {
				//logger.error("MemcachedException getVal key=".concat(key).concat(" :").concat(e.getMessage()));
			}
		}
		return null;
	}
}
