package com.zcc.demo;

import java.util.concurrent.atomic.AtomicInteger;

public class Demo {

	public static void main(String[] args) {
		AtomicInteger atomicInteger = new AtomicInteger();
		atomicInteger.compareAndSet(1,5);

	}
}
