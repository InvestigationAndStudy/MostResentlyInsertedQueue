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
        return 0;
    }

    @Override
    public void put(T t) throws InterruptedException {

    }

    @Override
    public boolean offer(T t, long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public T take() throws InterruptedException {
        return null;
    }

    @Override
    public T poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public int remainingCapacity() {
        return 0;
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
        return false;
    }

    @Override
    public T poll() {
        return null;
    }

    @Override
    public T peek() {
        return null;
    }
}
