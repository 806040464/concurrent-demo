package com.zcc.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class ShareResource {
	private Object obj;
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public void write(Object obj) {
		lock.writeLock().lock();
		try {
			this.obj = obj;
			System.out.println(Thread.currentThread().getName() + "\t" + obj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
	}

	public void read() {
		lock.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + "\t" + obj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.readLock().unlock();
		}
	}
}

public class Demo_ReadWrite {

	public static void main(String[] args) {
		ShareResource s = new ShareResource();
		new Thread(() -> {
			s.write("123465");
		}, "writelock").start();
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 100; i++) {
			new Thread(() -> {
				s.read();
			}, String.valueOf(i)).start();
		}
	}

}
