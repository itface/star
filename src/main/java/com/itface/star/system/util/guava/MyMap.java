package com.itface.star.system.util.guava;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

public class MyMap {

	public void test(){
		Map mapA = new HashMap();
		Map mapB = new HashMap();
		MapDifference differenceMap = Maps.difference(mapA, mapB);

		differenceMap.areEqual();
		Map entriesDiffering = differenceMap.entriesDiffering();
		Map entriesOnlyOnLeft = differenceMap.entriesOnlyOnLeft();
		Map entriesOnlyOnRight = differenceMap.entriesOnlyOnRight();
		Map entriesInCommon = differenceMap.entriesInCommon();
	}
}
