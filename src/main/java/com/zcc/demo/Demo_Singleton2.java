package com.zcc.demo;

/**
 * 静态内部类懒汉式单例
 *
 * 在内部类加载和初始化时才创建instance对象
 * 静态内部类不会自动随着外部类初始化而初始化，要单独去加载和初始化的
 */
public class Demo_Singleton2 {
	private static class Inner {
		private static final Demo_Singleton2 INSTANCE =new Demo_Singleton2();
	}

	private Demo_Singleton2() {

	}

	public static Demo_Singleton2 getInstance() {
		return Inner.INSTANCE;
	}

}
