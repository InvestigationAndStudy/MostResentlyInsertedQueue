package com.aquila.queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by venya on 29.07.17.
 */
public class MostRecentlyInsertedBlockingQueue<T>  extends ConcurrentMostRecentlyInsertedQueue<T> implements BlockingQueue<T> {

    public MostRecentlyInsertedBlockingQueue(int capacity) {
        super(capacity);
    }


    /**
     * Inserts the specified element into this queue, waiting if necessary for space to become available.
     * @param t
     * @throws InterruptedException
     */
    @Override
    public void put(T t) throws InterruptedException {
        this.add(t);
    }

    /**
     * Inserts the specified element into this queue, waiting up to the specified wait time if necessary for space
     * to become available.
     * @param t
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    @Override
    public boolean offer(T t, long timeout, TimeUnit unit) throws InterruptedException {
        return this.add(t);
    }

    /**
     * Retrieves and removes the head of this queue, waiting if necessary until an element becomes available.
     * @return
     * @throws InterruptedException
     */
    @Override
    public T take() throws InterruptedException {
        return this.remove();
    }

    @Override
    public T poll(long timeout, TimeUnit unit) throws InterruptedException {
        return this.poll();
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
}
