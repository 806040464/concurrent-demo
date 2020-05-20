package com.zcc.demo.juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class Demo_Callable {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		FutureTask<Integer> futureTask = new FutureTask<Integer>(() -> {
			System.out.println("**************"); return 1024;});

		new Thread(futureTask).start();

		TimeUnit.SECONDS.sleep(1);
		while (!futureTask.isDone()){}
		System.out.println(futureTask.get());

	}
}
