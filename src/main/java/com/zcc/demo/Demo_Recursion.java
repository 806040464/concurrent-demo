package com.zcc.demo;

/**
 * 有n个台阶，一次只能走1步/2步，共多少种走法？
 */
public class Demo_Recursion {

	public static int f(int n){
		if (n<1){
			throw new IllegalArgumentException();
		}
		if (n==1 || n==2){
			return n;
		}
		return f(n-1)+f(n-2);
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println(f(30));
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
}
