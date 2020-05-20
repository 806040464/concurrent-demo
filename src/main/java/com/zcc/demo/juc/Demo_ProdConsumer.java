package com.zcc.demo.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData{
	private int num = 0;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	public void increment(){
		lock.lock();
		try {
			while (num != 0){
				condition.await();
			}
			num++;
			System.out.println(Thread.currentThread().getName()+"\t "+num);
			condition.signalAll();
		}catch (Exception e){
		    e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}

	public void decrement(){
		lock.lock();
		try {
			while (num == 0){
				condition.await();
			}
			num--;
			System.out.println(Thread.currentThread().getName()+"\t "+num);
			condition.signalAll();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
}

public class Demo_ProdConsumer {

	public static void main(String[] args) {
		ShareData shareData = new ShareData();

		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				shareData.increment();
			}
		}, "A").start();

		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				shareData.decrement();
			}
		}, "B").start();

	}
}
