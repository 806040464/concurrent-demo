package com.zcc.demo.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Demo_SpinLock {

	AtomicReference<Thread> reference = new AtomicReference<>();

	public void myLock() {
		System.out.println(Thread.currentThread().getName() + "\t come in");
		Thread thread = Thread.currentThread();

		while (!reference.compareAndSet(null, thread)) {

		}
	}

	public void myUnLock() {
		Thread thread = Thread.currentThread();
		reference.compareAndSet(thread, null);
		System.out.println(Thread.currentThread().getName() + "\t invoked unlock method");
	}

	public static void main(String[] args) {
		Demo_SpinLock demo = new Demo_SpinLock();
		new Thread(() -> {
			demo.myLock();
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			demo.myUnLock();
		}, "AAA").start();

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		new Thread(() -> {
			demo.myLock();
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			demo.myUnLock();
		}, "BBB").start();
	}
}
