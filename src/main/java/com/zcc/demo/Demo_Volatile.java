package com.zcc.demo;

import java.util.concurrent.TimeUnit;

class MyData {
	volatile int num = 0;

	public void addTo60() {
		this.num = 60;
	}

	public void addPlusPlus() {
		num++;
	}
}

public class Demo_Volatile {

	public static void main(String[] args) {
		seeOk();
//		atmoic();
	}

	private static void atmoic() {
		MyData m = new MyData();

		for (int i = 0; i < 20; i++) {
			new Thread(() -> {
				for (int j = 0; j < 1000; j++) {
					m.addPlusPlus();
				}
			}, String.valueOf(i)).start();
		}

		//保证线程计算完成
		while (Thread.activeCount() > 2) {
			Thread.yield();
		}

		System.out.println(Thread.currentThread().getName() + "\t" + m.num);
	}

	private static void seeOk() {
		MyData m = new MyData();

		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + "\t coming in");
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			m.addTo60();
			System.out.println(Thread.currentThread().getName() + "\t updated value:" + m.num);
		}, "AA").start();

		while (m.num == 0) {

		}

		System.out.println(Thread.currentThread().getName() + "\t mission is over");
	}
}
