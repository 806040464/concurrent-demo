package com.zcc.demo.concurrent;

public class InterruptedDemo {

    //interrupted()返回是否中断，并会清除当前线程中断标志
    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    System.out.println("廷珏");
                }
                System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().isInterrupted());
            }
        });

        threadOne.start();
        Thread.sleep(1000);
        threadOne.interrupt();
        System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().isInterrupted());
        threadOne.join();
        System.out.println("main is over");
    }
}
