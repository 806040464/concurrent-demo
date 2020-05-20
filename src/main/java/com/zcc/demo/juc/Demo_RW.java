package com.zcc.demo.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class myCache {
	private volatile Map<String, Object> map = new HashMap<>();
	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

	public void put(String key, Object value) {
		rwLock.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + "\t 开始写入：" + key);
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			map.put(key, value);
			System.out.println(Thread.currentThread().getName() + "\t 写入完成");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rwLock.writeLock().unlock();
		}
	}

	public void get(String key) {
		rwLock.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + "\t 开始读取");
			Object result = map.get(key);
			System.out.println(Thread.currentThread().getName() + "\t 读取完成：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rwLock.readLock().unlock();
		}
	}
}

public class Demo_RW {

	public static void main(String[] args) {
		myCache cache = new myCache();

		for (int i = 0; i < 5; i++) {
			final int temp = i;
			new Thread(() -> {
				cache.put(temp + "", temp + "");
			}, String.valueOf(i)).start();
		}

		for (int i = 0; i < 5; i++) {
			final int temp = i;
			new Thread(() -> {
				cache.get(temp + "");
			}, String.valueOf(i)).start();
		}
	}

}
