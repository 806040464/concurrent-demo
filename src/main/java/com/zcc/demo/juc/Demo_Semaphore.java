package com.zcc.demo.juc;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Demo_Semaphore {

	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(3);
		LinkedBlockingQueue blockingQueue = new LinkedBlockingQueue();
		for (int i = 1; i <= 6; i++) {
			new Thread(() -> {
				try {
					semaphore.acquire();
					System.out.println(Thread.currentThread().getName() + "\t 进入车位");
					try {
						TimeUnit.SECONDS.sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + "\t 停三秒后离开车位");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					semaphore.release();
				}
			}, String.valueOf(i)).start();
		}
	}
}
