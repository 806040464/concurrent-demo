package com.zcc.demo.concurrent;

public class NotifyAllDemo {
    private static volatile Object resourceA = new Object();

    //如果调用notifyall()之后，线程调用该共享变量的wait()放入阻塞集合，该线程不会被唤醒
    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println(Thread.currentThread().getName() + "get resourceA lock");
                try {
                    System.out.println(Thread.currentThread().getName() + "resourceA begin wait");
                    resourceA.wait();
                    System.out.println(Thread.currentThread().getName() + "resourceA end wait");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread threadB = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println(Thread.currentThread().getName() + "get resourceA lock");
                try {
                    System.out.println(Thread.currentThread().getName() + "resourceA begin wait");
                    resourceA.wait();
                    System.out.println(Thread.currentThread().getName() + "resourceA end wait");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread threadC = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println(Thread.currentThread().getName() + "begin notify");
//                resourceA.notifyAll();
                resourceA.notify();
            }
        });

        threadA.start();
        threadB.start();
        Thread.sleep(1000);
        threadC.start();

        threadA.join();
        threadB.join();
        threadC.join();
        System.out.println(Thread.currentThread().getName() + "over");
    }
}
