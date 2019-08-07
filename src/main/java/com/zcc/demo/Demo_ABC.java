package com.zcc.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData1 {
	private volatile int num = 1;
	private Lock lock = new ReentrantLock();
	private Condition condition1 = lock.newCondition();
	private Condition condition2 = lock.newCondition();
	private Condition condition3 = lock.newCondition();

	public void print5() {
		lock.lock();
		try {
			while (num != 1) {
				condition1.await();
			}
			for (int i = 0; i < 5; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i);
			}
			num = 2;
			condition2.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void print10() {
		lock.lock();
		try {
			while (num != 2) {
				condition2.await();
			}
			for (int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i);
			}
			num = 3;
			condition3.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void print15() {
		lock.lock();
		try {
			while (num != 3) {
				condition3.await();
			}
			for (int i = 0; i < 15; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i);
			}
			num = 1;
			condition1.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

}

public class Demo_ABC {

	public static void main(String[] args) {
		ShareData1 shareData1 = new ShareData1();

		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				shareData1.print5();
			}
		}, "A").start();
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				shareData1.print10();
			}
		}, "B").start();
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				shareData1.print15();
			}
		}, "C").start();

	}
}
