package com.zcc.demo;

import java.util.concurrent.CountDownLatch;

public class Demo_CountDownLatch {

	public static void main(String[] args) {

//		closeDoor();
		CountDownLatch countDownLatch = new CountDownLatch(6);
		for (int i = 1; i <= 6; i++) {
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName()+"\t国灭亡");
				countDownLatch.countDown();
			}, CountryEnum.forEach(i).getCountry()).start();
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+"\t秦国一统天下");

	}

	private static void closeDoor() {
		CountDownLatch countDownLatch = new CountDownLatch(6);

		for (int i = 1; i <= 6 ; i++) {
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName()+"\t 同学离开教室");
				countDownLatch.countDown();
			}, String.valueOf(i)).start();
		}

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+"\t ************班长离开教室");
	}
}
