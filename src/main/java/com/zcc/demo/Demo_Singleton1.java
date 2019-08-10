package com.zcc.demo;

import java.io.IOException;
import java.util.Properties;

/**
 * 静态代码块饿汉式单例，主要用于复杂实例化
 */
public class Demo_Singleton1 {
	private static final Demo_Singleton1 INSTANCE;
	private String info;

	static {
		//这里获取配置文件的属性值  可通过@Value注解更加方便
		Properties properties = new Properties();
		try {
			properties.load(Demo_Singleton1.class.getClassLoader().getResourceAsStream("singlton.properties"));
			INSTANCE = new Demo_Singleton1(properties.getProperty("info"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private Demo_Singleton1(String info){
		this.info = info;
	}

	public static void main(String[] args) {
		new Thread(() -> {
			Demo_Singleton1 demo = Demo_Singleton1.INSTANCE;
			System.out.println(demo);
		}, "aa").start();
		new Thread(() -> {
			Demo_Singleton1 demo = Demo_Singleton1.INSTANCE;
			System.out.println(demo);
		}, "bb").start();
	}

}
