package com.itface.star.system.util.guava;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ListAndMap {

	public void createListAndMap(){
		/*
		 * List<String> list = new ArrayList<String>();  
		 * 写那个<>里面的东东两次就觉得累得慌, 
		 * Lists和Maps是两个工具类, Lists.newArrayList()其实和new ArrayList()几乎一模一样, 唯一它帮你做的(其实是javac帮你做的), 就是自动推导(不是"倒")尖括号里的那坨叉叉.
		 */
		List<String> list = Lists.newArrayList();
		/*
		 * List<Integer> list = new ArrayList<Integer>();
			list.add(1);
			list.add(2);
			list.add(3);
		 */
		List<Integer> list2 = Lists.newArrayList(1, 2, 3);
		/*
		 * Map<String, Integer> map = new HashMap<String, Integer>();
		 */
		Map<String, Integer> map = Maps.newHashMap();
	}
	
	public void immutableCollections(){
		/*
		 * 大家都用过 Collections.unmodifiableXXX() 来做一个不可修改的集合。例如你要构造存储常量的 Set，你可以这样来做 :
 Set<String> set = new HashSet<String>(Arrays.asList(new String[]{"RED", "GREEN"})); 
 Set<String> unmodifiableSet = Collections.unmodifiableSet(set); 
这看上去似乎不错，因为每次调 unmodifiableSet.add() 都会抛出一个 UnsupportedOperationException。感觉安全了？慢！如果有人在原来的 set 上 add 或者 remove 元素会怎么样？结果 unmodifiableSet 也是被 add 或者 remove 元素了。
		 */
		ImmutableSet<String> immutableSet = ImmutableSet.of("RED", "GREEN");
		/*
		 * 就这样一句就够了，而且试图调 add 方法的时候，它一样会抛出 UnsupportedOperationException。重要的是代码的可读性增强了不少，非常直观地展现了代码的用意。如果像之前这个代码保护一个 set 怎么做呢？你可以 :
		 */
		Set<String> set = new HashSet<String>();
		ImmutableSet<String> immutableSet2 = ImmutableSet.copyOf(set);
		ImmutableMap<String, Integer> map = ImmutableMap.of(
			    "1", 1,
			    "2", 2,
			    "3", 3
			);
		Builder<String>  builder = ImmutableSet.builder(); 
		ImmutableSet<String> immutableSet3 = builder.add("RED").addAll(set).build(); 
		 
		 
		ImmutableMap.Builder<String, Integer> builder2 = ImmutableMap.builder();
		builder2.put("name", 23);
		ImmutableMap<String, Integer> map2 = builder2.build();
	}
	/**
	 * 把重复的元素放入集合,譬如一个 List 里面有各种字符串，然后你要统计每个字符串在 List 里面出现的次数
	 */
	public void multiset(){
		HashMultiset<String> multiSet = HashMultiset.create(); 
		multiSet.addAll(new ArrayList<String>()); 
		 //count word “the”
		Integer count = multiSet.count("the"); 
	}
	/**
	 * 在 Map 的 value 里面放多个元素,举个记名投票的例子。所有选票都放在一个 List<Ticket> 里面，List 的每个元素包括投票人和选举人的名字
	 */
	public void multimap(){
		HashMultimap<String, String> map = HashMultimap.create(); 
		map.put("bush", "lala"); 
		map.put("bush", "lili");
		map.put("obama", "lilei");
		
	}
	/**
	 * BiMap 实现了 java.util.Map 接口。它的特点是它的 value 和它 key 一样也是不可重复的，换句话说它的 key 和 value 是等价的。
	 * 举个例子，你可能经常会碰到在 Map 里面根据 value 值来反推它的 key 值的逻辑
	 */
	public void biMap(){
		/*
		biMap.inverse().get(anAddess);
		这里的 inverse 方法就是把 BiMap 的 key 集合 value 集合对调，因此 biMap == biMap.inverse().inverse()。
				BiMap的常用实现有：
				HashBiMap: key 集合与 value 集合都有 HashMap 实现
				EnumBiMap: key 与 value 都必须是 enum 类型
				ImmutableBiMap: 不可修改的 BiMap
				*/
	}
}
