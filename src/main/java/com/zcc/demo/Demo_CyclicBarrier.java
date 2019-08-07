package com.zcc.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Demo_CyclicBarrier {

	public static void main(String[] args) {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(7, ()->{System.out.println(Thread.currentThread().getName()+"\t***召唤神龙");});

		for (int i = 0; i < 7; i++) {
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName()+"\t 龙珠集齐");
				try {
					cyclicBarrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}, String.valueOf(i)).start();
		}

	}
}
