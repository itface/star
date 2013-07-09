package com.itface.star.system.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

public class OrderedProperties extends Properties {

	
	private static final long serialVersionUID = -8943516439497785723L;
	
	private final Set<Object> keys = new LinkedHashSet<Object>();

	public Enumeration<Object> keys() {
		return Collections.<Object> enumeration(keys);
	}
	public Object put(Object key, Object value) {
		keys.add(key);
		return super.put(key, value);
	}
	public Set<Object> keySet() {
		return keys;
	}
	public Set<String> stringPropertyNames() {
		Set<String> set = new LinkedHashSet<String>();
		for (Object key : this.keys) {
			set.add((String) key);
		}
		return set;
	}
}
