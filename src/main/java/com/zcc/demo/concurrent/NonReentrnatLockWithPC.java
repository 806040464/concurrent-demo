package com.zcc.demo.concurrent;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;

/**
 * 使用自定义锁实现生产-消费模型
 *
 * @author zhaoccf
 * @date 2020/5/20 15:09
 * @since 1.8
 **/
public class NonReentrnatLockWithPC {

    private final static NonReentrantLock lock = new NonReentrantLock();
    private final static Condition notFull = lock.newCondition();
    private final static Condition notEmpty = lock.newCondition();

    private final static Queue<String> queue = new LinkedBlockingQueue<String>();
    private final static int queueSize = 9;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                //获取独占锁
                lock.lock();
                try {
                    //如果队列满了，则等待
                    if (queueSize == queue.size()) {
                        System.out.println(Thread.currentThread().getName() + "队列满了，等待");
                        notEmpty.await();
                    }
                    //添加元素到队列
                    queue.add("ele");
                    //唤醒消费线程
                    System.out.println(Thread.currentThread().getName() + "唤醒消费线程");
                    notFull.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //释放锁
                    lock.unlock();
                }
            }).start();
        }
        Thread.sleep(1000);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                //获取独占锁
                lock.lock();
                try {
                    //队列空则等待
                    if (0 == queue.size()) {
                        System.out.println(Thread.currentThread().getName() + "队列空，等待");
                        notFull.await();
                    }
                    //消费一个元素
                    String ele = queue.poll();
                    System.out.println(ele);
                    System.out.println(Thread.currentThread().getName() + "唤醒生产线程");
                    notEmpty.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //释放锁
                    lock.unlock();
                }
            }).start();
        }
    }
}
