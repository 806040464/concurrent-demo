package com.zcc.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZccDemoApplicationTests {

	@Test
	public void contextLoads() {
		new Thread(() -> {
			Demo_Singleton2 instance = Demo_Singleton2.getInstance();
			System.out.println(instance);
		}, "aa").start();
		new Thread(() -> {
			Demo_Singleton2 instance = Demo_Singleton2.getInstance();
			System.out.println(instance);
		}, "bb").start();

		AtomicInteger atomicInteger = new AtomicInteger();
		atomicInteger.getAndIncrement();
	}

}
