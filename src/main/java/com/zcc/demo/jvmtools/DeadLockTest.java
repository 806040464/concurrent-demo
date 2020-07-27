package com.zcc.demo.jvmtools;

/**
 * @author zhaoccf
 * @Description:
 * @date: 2020/7/9 16:24
 * @since 1.8
 */
public class DeadLockTest {

    static class DeadLock implements Runnable {
        String a, b;

        public DeadLock(String a, String b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            synchronized (a) {
                synchronized (b) {
                    System.out.println(a + b);
                }
            }
        }
    }

    public static void main(String[] args) {
        String lock1 = "lock1";
        String lock2 = "lock2";
        for (int i = 0; i < 1000; i++) {
            new Thread(new DeadLock(lock1, lock2), "deadlock-test-1").start();
            new Thread(new DeadLock(lock2, lock1), "deadlock-test-2").start();
        }
    }

}
