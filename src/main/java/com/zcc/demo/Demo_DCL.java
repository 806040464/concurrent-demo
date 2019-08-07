package com.zcc.demo;

public class Demo_DCL {

	private static volatile Demo_DCL instance = null;

	private Demo_DCL() {
		System.out.println(Thread.currentThread().getName()+"\t 我是一个构造方法");
	}

	public static Demo_DCL getInstance() {
		if (instance == null){
			synchronized (Demo_DCL.class){
				if (instance == null){
					instance = new Demo_DCL();
				}
			}
		}
		return instance;
	}

	public static void main(String[] args) {
//		System.out.println(Demo_DCL.getInstance() == Demo_DCL.getInstance());
//		System.out.println(Demo_DCL.getInstance() == Demo_DCL.getInstance());
//		System.out.println(Demo_DCL.getInstance() == Demo_DCL.getInstance());

		for (int i=0; i<10;i++){
		    new Thread(() -> {
		    	Demo_DCL.getInstance();
		    }, String.valueOf(i)).start();
		}
	}
}
