package com.zcc.demo.jvmtools;

/**
 * @author zhaoccf
 * @Description:
 * @date: 2020/7/9 16:15
 * @since 1.8
 */
public class ThreadTest {

    public static void create1() {
        Thread thread1 = new Thread(() -> {
            while (true) {
                ;
            }
        }, "thread-test-1");
        thread1.start();
    }

    public static void create2(final Object lock) {
        Thread thread2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread-test-2");
        thread2.start();
    }

    public static void main(String[] args) {
        Object lock = new Object();
        create1();
        create2(lock);
    }
}
