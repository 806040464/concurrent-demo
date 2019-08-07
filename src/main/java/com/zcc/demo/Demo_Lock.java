package com.zcc.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Resource {
	private int flag = 1;
	private Lock lock = new ReentrantLock();
	private Condition condition1 = lock.newCondition();
	private Condition condition2 = lock.newCondition();
	private Condition condition3 = lock.newCondition();

	public void print5(int loop) {
		lock.lock();
		try {
			while (flag != 1) {
				condition1.await();
			}
			for (int i = 0; i < 5; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i + "\t loop:" + loop);
			}
			flag = 2;
			condition2.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void print10(int loop) {
		lock.lock();
		try {
			while (flag != 2) {
				condition2.await();
			}
			for (int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i + "\t loop:" + loop);
			}
			flag = 3;
			condition3.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void print15(int loop) {
		lock.lock();
		try {
			while (flag != 3) {
				condition3.await();
			}
			for (int i = 0; i < 15; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i + "\t loop:" + loop);
			}
			flag = 1;
			condition1.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}

public class Demo_Lock {

	public static void main(String[] args) {
		Resource r = new Resource();

		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				r.print5(i);
			}
		}, "AA").start();
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				r.print10(i);
			}
		}, "BB").start();
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				r.print15(i);
			}
		}, "CC").start();
	}
}
