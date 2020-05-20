package com.zcc.demo.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class ShareData2 {
	private volatile boolean Flag = true;
	private AtomicInteger atomicInteger = new AtomicInteger();
	private BlockingQueue<String> blockingQueue = null;

	public ShareData2(BlockingQueue<String> blockingQueue) {
		System.out.println(blockingQueue.getClass().getName());
		this.blockingQueue = blockingQueue;
	}

	public void increment() {
		String data = null;
		boolean result;
		while (Flag) {
			data = atomicInteger.getAndIncrement() + "";
			try {
				result = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
				if (result) {
					System.out.println(Thread.currentThread().getName() + "\t 生产成功" + data);
				} else {
					System.out.println(Thread.currentThread().getName() + "\t 生产失败" + data);
				}
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + "\t boss停止生产");
	}

	public void decrement() {
		String poll;
		while (Flag) {
			try {
				poll = blockingQueue.poll(2L, TimeUnit.SECONDS);
				if (null == poll || "".equalsIgnoreCase(poll)) {
					System.out.println(Thread.currentThread().getName() + "\t无法消费，消费退出");
//					Flag = false;
					return;
				}
				System.out.println(Thread.currentThread().getName() + "\t 消费成功" + poll);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stop() {
		this.Flag = false;
	}
}

public class Demo_ProdConsumerBQ {

	public static void main(String[] args) {

		System.out.println(Runtime.getRuntime().availableProcessors());

		ShareData2 shareData2 = new ShareData2(new ArrayBlockingQueue<String>(3));

		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + "\t 生产者线程启动");
			shareData2.increment();
		}, "Prod").start();
		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + "\t 消费者线程启动");
			System.out.println();
			System.out.println();
			shareData2.decrement();
			System.out.println();
			System.out.println();
		}, "Consumer").start();

		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println();
		System.out.println();
		shareData2.stop();

	}
}
