package com.zcc.demo.concurrent;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
  *
  * @since 1.8
  * @author zhaoccf
  * @date 2020/5/20 15:03
  **/
public class NonReentrantLock implements Lock, Serializable {

    private static final long serialVersionUID = -6711538354384902250L;

    private static class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = -4974218993048719361L;

        //是否锁已经被持有
        @Override
        protected boolean isHeldExclusively() {
            return 1 == getState();
        }

        //如果state为0，则尝试获取锁
        @Override
        protected boolean tryAcquire(int acquires) {
            assert 1 == acquires;
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        //尝试释放锁，设置state为0
        @Override
        protected boolean tryRelease(int releases) {
            assert 1 == releases;
            if (0 == getState()) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        //提供条件变量接口
        Condition newCondition() {
            return new ConditionObject();
        }
    }

    //创建一个sync来做具体的工作
    private final Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
