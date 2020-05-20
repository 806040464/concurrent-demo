package com.zcc.demo.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Demo_BlockingQueue {

	public static void main(String[] args) {
//		arrayBlockingQueue();
//		linkedBlockingQueue();
//		putTake();
		BlockingQueue blockingQueue = new LinkedBlockingQueue(3);
		try {
			System.out.println(blockingQueue.offer("1", 2L, TimeUnit.SECONDS));
			System.out.println(blockingQueue.offer("2", 2L, TimeUnit.SECONDS));
			System.out.println(blockingQueue.offer("3", 2L, TimeUnit.SECONDS));

			System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
			System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
			System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private static void putTake() {
		//		BlockingQueue blockingQueue = new SynchronousQueue();
		BlockingQueue blockingQueue = new LinkedBlockingQueue(3);
		try {
			blockingQueue.put("1");
			blockingQueue.put("2");
			blockingQueue.put("3");
			blockingQueue.take();
			blockingQueue.take();
			blockingQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void linkedBlockingQueue() {
		BlockingQueue blockingQueue = new LinkedBlockingQueue(3);
		System.out.println(blockingQueue.offer("1"));
		System.out.println(blockingQueue.offer("2"));
		System.out.println(blockingQueue.offer("3"));
//		System.out.println(blockingQueue.offer("3"));
		System.out.println(blockingQueue.peek());

		System.out.println(blockingQueue.poll());
		System.out.println(blockingQueue.poll());
		System.out.println(blockingQueue.poll());
	}

	private static void arrayBlockingQueue() {
		BlockingQueue blockingQueue = new ArrayBlockingQueue(3);

		blockingQueue.add("1");
		blockingQueue.add("1");
		blockingQueue.add("1");
//		blockingQueue.add("1");

		blockingQueue.remove();
		blockingQueue.remove();
		blockingQueue.remove();
		blockingQueue.remove();
	}
}
