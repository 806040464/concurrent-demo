package com.zcc.demo;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class Demo_ABA {

	static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
	static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, new Random().nextInt(100));

	public static void main(String[] args) {

		System.out.println("==============以下是ABA问题的产生==============");

		new Thread(() -> {
			atomicReference.compareAndSet(100, 101);
			atomicReference.compareAndSet(101, 100);
			System.out.println(Thread.currentThread().getName() + "\t" + atomicReference.get());
		}, "t1").start();

		new Thread(() -> {
			boolean b = atomicReference.compareAndSet(100, 2019);
			System.out.println(Thread.currentThread().getName() + "\t" + b + "\t" + atomicReference.get());
		}, "t2").start();

		System.out.println("==============以下是ABA问题的解决==============");

		new Thread(() -> {
			int stamp = atomicStampedReference.getStamp();
			System.out.println(Thread.currentThread().getName() + "\t第一次时间戳：" + stamp);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
			System.out.println(Thread.currentThread().getName()+"\t第二次时间戳："+atomicStampedReference.getStamp());
			atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
			System.out.println(Thread.currentThread().getName()+"\t第三次时间戳："+atomicStampedReference.getStamp());
		}, "t3").start();

		new Thread(() -> {
			int stamp = atomicStampedReference.getStamp();
			System.out.println(Thread.currentThread().getName() + "\t第一次时间戳：" + stamp);
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			boolean b = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1);
			System.out.println(Thread.currentThread().getName()+"\t修改成功与否："+b+"\t"+atomicStampedReference.getReference());
		}, "t4").start();

	}
}
