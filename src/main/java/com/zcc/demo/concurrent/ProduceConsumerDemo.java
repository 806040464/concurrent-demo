package com.zcc.demo.concurrent;

import java.util.ArrayList;
import java.util.List;

public class ProduceConsumerDemo {

    //1. wait notify notifyall为object类的方法
    //2. 调用wait notify notifyall方法需要获取该对象的监视器锁，否则会抛出IllegalMonitorStateException异常
    //3. 一个线程可以从挂起状态变为可运行状态，即使没有被其他线程调用notify，notifyall方法进行通知、被中断、等待超时，就是虚假唤醒
    //4. 防止虚假唤醒就是循环判断唤醒条件
    public static void main(String[] args) {
        int max = 1;
        List list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (list) {
                    while (list.size() == max) {
                        try {
                            list.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    list.add("obj");
                    System.out.println(Thread.currentThread().getName() + "生产者生产");
                    list.notifyAll();
                }
            }).start();
            new Thread(() -> {
                synchronized (list) {
                    while (list.size() == 0) {
                        try {
                            list.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    list.remove(0);
                    System.out.println(Thread.currentThread().getName() + "消费者消费");
                    list.notifyAll();
                }
            }).start();
        }
    }
}
