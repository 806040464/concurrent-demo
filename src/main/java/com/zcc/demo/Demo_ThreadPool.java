package com.zcc.demo;

import java.util.concurrent.*;

public class Demo_ThreadPool {

	public static void main(String[] args) {
//		executorsInit();

		ExecutorService threadPool = new ThreadPoolExecutor(
				2,
				5,
				1L,
				TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(3),
				Executors.defaultThreadFactory(),
				new ThreadPoolExecutor.DiscardPolicy());
		try {
			for (int i = 0; i < 30; i++) {
				threadPool.execute(() -> {System.out.println(Thread.currentThread().getName()+"\t 办理业务");});
//				Future<Integer> submit = threadPool.submit(() -> {
//					return 1024;
//				});
//				Integer integer = submit.get();
//				System.out.println(integer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			threadPool.shutdown();
		}

	}

	private static void executorsInit() {
		ExecutorService threadPool = Executors.newFixedThreadPool(5);
//		ExecutorService threadPool = Executors.newSingleThreadExecutor();
//		ExecutorService threadPool = Executors.newCachedThreadPool();
//		ExecutorService threadPool = Executors.newScheduledThreadPool(5);

		try {
			for (int i = 0; i < 10; i++) {
				threadPool.execute(() -> {System.out.println(Thread.currentThread().getName()+"\t 办理业务");});
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			threadPool.shutdown();
		}
	}

}
