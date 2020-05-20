package com.zcc.demo.juc;

/**
 * 有n个台阶，一次只能走1步/2步，共多少种走法？
 */
public class Demo_Loop {

	public static int f(int n) {
		if (n < 1) {
			throw new IllegalArgumentException();
		}
		if (n == 1 || n == 2) {
			return n;
		}
		//n>=3的情况
		int one = 2;
		int two = 1;
		int sum = 3;
		for (int i = 3; i <= n; i++) {
			sum = one + two;
			two = one;
			one = sum;
		}
		return sum;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println(f(30));
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
}
