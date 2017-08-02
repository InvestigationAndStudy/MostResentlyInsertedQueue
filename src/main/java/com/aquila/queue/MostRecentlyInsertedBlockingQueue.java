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
        this.queuecount = 0;
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
            while (this.queuecount == this.MAX_CAPACITY) {
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
            while (this.queuecount == this.MAX_CAPACITY) {
                notFull.awaitNanos(nanoseconds);
            }
            this.insert(t);
            return true;
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean offer(T t) {
        this.lock.lock();
        try {
            if (this.queuecount == this.MAX_CAPACITY) {
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
    public T poll(long timeout, TimeUnit unit) throws InterruptedException {
        long nanoseconds = unit.toNanos(timeout);
        this.lock.lockInterruptibly();
        try {
            while (this.queuecount == 0) {
                notEmpty.awaitNanos(nanoseconds);
            }
            return rem();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public T poll() {
        this.lock.lock();
        try {
            if (this.queuecount == 0) {
                return null;
            } else {
                return this.rem();
            }
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
            return this.rem();
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

    /**
     * Not implemented
     * @param c
     * @return
     */
    @Override
    public int drainTo(Collection<? super T> c) {
        return 0;
    }

    /**
     * Not implementd
     * @param c
     * @param maxElements
     * @return
     */
    @Override
    public int drainTo(Collection<? super T> c, int maxElements) {
        return 0;
    }


    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     * @return
     */
    @Override
    public T peek() {
        this.lock.lock();
        try {
            if (this.queuecount == 0 ) {
                return null;
            } else {
                return this.get();
            }
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public String toString(){
        return this.queuelist.toString();
    }

    private void insert(T t) {
        this.queuelist.add(t);
        ++this.queuecount;
        this.notEmpty.signal();
    }

    private T rem() {
        T t = this.queuelist.remove(0);
        --this.queuecount;
        this.notFull.signal();
        return t;
    }

    private T get() {
        T t = this.queuelist.get(0);
        this.notFull.signal();
        return t;
    }
}
