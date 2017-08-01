package com.aquila.queue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;


public class MostRecentlyInsertedBlockingQueue<T> extends java.util.AbstractQueue<T> implements BlockingQueue<T> {
    private final ReentrantLock lock;
    private final Condition notEmpty;
    private final Condition notFull;
    private final List<T> queuelist;
    private final int MAX_CAPACITY;
    private int queuecount;

    public MostRecentlyInsertedBlockingQueue(int capacity) {
        this.MAX_CAPACITY = capacity;
        this.queuelist = new ArrayList<T>(this.MAX_CAPACITY);
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.notFull =  lock.newCondition();
    }


    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public int size() {
        this.lock.lock();
        try {
            return this.queuecount;
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void put(T t) throws InterruptedException {
        this.lock.lockInterruptibly();
        try {
            while (this.queuecount == this.queuelist.size()) {
                this.notFull.await();
            }
            this.insert(t);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean offer(T t, long timeout, TimeUnit unit) throws InterruptedException {
        long nanoseconds = unit.toNanos(timeout);
        this.lock.lockInterruptibly();
        try {
            while (this.queuecount == this.queuelist.size()) {
                notFull.awaitNanos(nanoseconds);
            }
            this.insert(t);
            return true;
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public T poll(long timeout, TimeUnit unit) throws InterruptedException {
        long nanoseconds = unit.toNanos(timeout);
        this.lock.lockInterruptibly();
        try {
            while (this.queuecount == 0) {
                notEmpty.awaitNanos(nanoseconds);
            }
            return extract();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public T take() throws InterruptedException {
        this.lock.lockInterruptibly();
        try {
            while (this.queuecount == 0) {
                this.notEmpty.await();
            }
            return this.extract();
        } finally {
            this.lock.unlock();
        }

    }

    @Override
    public int remainingCapacity() {
        this.lock.lock();
        try {
            return this.queuelist.size();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public int drainTo(Collection<? super T> c) {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super T> c, int maxElements) {
        return 0;
    }

    @Override
    public boolean offer(T t) {
        this.lock.lock();
        try {
            if (this.queuecount == this.queuelist.size()) {
                return false;
            } else {
                this.insert(t);
                return true;
            }
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public T poll() {
        return null;
    }

    @Override
    public T peek() {
        return null;
    }

    private void insert(T t) {
        this.queuelist.add(t);
        this.notEmpty.signal();
    }

    private T extract() {
        T t = this.queuelist.remove(0);
        this.notFull.signal();
        return t;
    }
}
