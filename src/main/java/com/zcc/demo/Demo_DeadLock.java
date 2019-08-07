package com.zcc.demo;

import java.util.concurrent.TimeUnit;

class ThreadHoldLock implements Runnable {
	private String lockA;
	private String lockB;

	ThreadHoldLock(String lockA, String lockB) {
		this.lockA = lockA;
		this.lockB = lockB;
	}

	@Override
	public void run() {
		synchronized (lockA) {
			System.out.println(Thread.currentThread().getName() + "\t 当前持有：" + lockA + "试图获取：" + lockB);
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (lockB) {
				System.out.println(Thread.currentThread().getName() + "\t 当前持有：" + lockB + "试图获取：" + lockA);
			}
		}
	}
}

/**
 * 死锁实质两个或两个以上进程，在执行过程中因争夺资源而造成的相互等待的现象
 * 若无外力干涉，程序无法推进下去
 */
public class Demo_DeadLock {

	public static void main(String[] args) {
		String lock1 = "lock1";
		String lock2 = "lock2";

		new Thread(new ThreadHoldLock(lock1, lock2), "AA").start();
		new Thread(new ThreadHoldLock(lock2, lock1), "BB").start();

	}
}
