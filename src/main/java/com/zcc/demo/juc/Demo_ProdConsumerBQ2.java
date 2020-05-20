package com.zcc.demo.juc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class ShareData3 {

	private volatile boolean Flag = true;
	private AtomicInteger atomicInteger = new AtomicInteger();
	BlockingQueue<String> blockingQueue = null;

	public ShareData3(BlockingQueue<String> blockingQueue) {
		this.blockingQueue = blockingQueue;
		System.out.println(blockingQueue.getClass().getName());
	}

	public void myProd() throws InterruptedException {
		String data = null;
		boolean result;
		while (Flag) {
			data = atomicInteger.getAndIncrement() + "";
			result = blockingQueue.offer(data, 1, TimeUnit.SECONDS);
			if (result) {
				System.out.println(Thread.currentThread().getName() + "\t 生产" + data + "成功");
			} else {
				System.out.println(Thread.currentThread().getName() + "\t 生产" + data + "失败");
			}
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + "\t 生产线程叫停退出");
	}

	public void myConsumer() throws InterruptedException {
		String data = null;
		while (Flag) {
			data = blockingQueue.poll(2, TimeUnit.SECONDS);
			if (null == data || data.equalsIgnoreCase("")) {
				System.out.println(Thread.currentThread().getName() + "\t 消费线程无法消费退出");
				return;
			}
			System.out.println(Thread.currentThread().getName() + "\t 消费" + data + "成功");
		}
	}

	public void stop() {
		this.Flag = false;
	}
}

public class Demo_ProdConsumerBQ2 {

	public static void main(String[] args) {
		ShareData3 data3 = new ShareData3(new LinkedBlockingQueue<>(3));
		System.out.println(Runtime.getRuntime().availableProcessors());

		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + "\t 生产线程启动");
			try {
				data3.myProd();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "Prod").start();

		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + "\t 消费线程启动");
			System.out.println();
			System.out.println();
			try {
				data3.myConsumer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "Consumer").start();

		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println();
		System.out.println();

		System.out.println(Thread.currentThread().getName() + "\t 大boss叫停");
		data3.stop();

	}
}
