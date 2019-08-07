package com.zcc.demo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Demo_ContainerNotSafe {

	public static void main(String[] args) {
//		List<String> list = getList();


//		Map<String, Object> map = new HashMap<>();
//		Map<String, Object> map = Collections.synchronizedMap(new HashMap<>());
		Map<String, Object> map = new ConcurrentHashMap();
		for (int i = 0; i < 30; i++) {
			new Thread(() -> {
				map.put(UUID.randomUUID().toString().substring(0, 8),1);
				System.out.println(map);
			}, String.valueOf(i)).start();
		}

	}

	private static List<String> getList() {
		//		List<String> list = new ArrayList<>();
//		List<String> list = Collections.synchronizedList(new ArrayList<>());
		List<String> list = new CopyOnWriteArrayList();
		for (int i = 0; i < 30; i++) {
			new Thread(() -> {
				list.add(UUID.randomUUID().toString().substring(0, 8));
				System.out.println(list);
			}, String.valueOf(i)).start();
		}
		return list;
	}
}
