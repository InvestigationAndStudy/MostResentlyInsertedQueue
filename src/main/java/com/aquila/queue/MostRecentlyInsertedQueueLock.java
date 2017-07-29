package com.aquila.queue;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MostRecentlyInsertedQueueLock<T> implements Queue<T> {
    private Lock lock = new ReentrantLock();
    private final List<T> queuelist;
    private final int MAX_CAPACITY;

    public MostRecentlyInsertedQueueLock(final int capacity) {
        this.MAX_CAPACITY = capacity;
        this.queuelist = new ArrayList<T>(this.MAX_CAPACITY);
    }


    @Override
    public int size() {
        lock.lock();
        int s;
        s = this.queuelist.size();
        lock.unlock();
        return s;
    }

    @Override
    public boolean isEmpty() {
        lock.lock();
        boolean b;
        b = this.queuelist.isEmpty();
        lock.unlock();
        return b;
    }

    @Override
    public boolean contains(Object o) {
        lock.lock();
        boolean b;
        b = this.queuelist.contains(o);
        lock.unlock();
        return b;
    }

    @Override
    public Iterator<T> iterator() {
        lock.lock();
        Iterator<T> i;
        i = this.queuelist.iterator();
        lock.unlock();
        return i;
    }

    @Override
    public Object[] toArray() {
        lock.lock();
        Object[] o;
        o = this.queuelist.toArray();
        lock.unlock();
        return o;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        lock.lock();
        T1[] t;
        t = this.queuelist.toArray(a);
        lock.unlock();
        return t;
    }

    @Override
    public boolean remove(Object o) {
        synchronized (this.queuelist) {
            return this.queuelist.remove(o);
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        lock.lock();
        boolean b;
        b = this.queuelist.containsAll(c);
        lock.unlock();
        return b;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        lock.lock();
        boolean b;
        b = this.queuelist.addAll(c);
        lock.unlock();
        return b;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        lock.lock();
        boolean b;
        b = this.queuelist.removeAll(c);
        lock.unlock();
        return b;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        lock.lock();
        boolean b;
        b = this.queuelist.retainAll(c);
        lock.unlock();
        return b;
    }

    @Override
    public void clear() {
        lock.lock();
        this.queuelist.clear();
        lock.unlock();
    }

    /**
     * Inserts the specified element into this queue if it is possible to do so immediately without violating
     *  capacity restrictions, returning true upon success and throwing an IllegalStateException if no space is
     *  currently available.
     * @param t
     * @return
     */
    @Override
    public boolean add(T t) throws IllegalStateException {
        lock.lock();
        if (this.queuelist.size() > this.MAX_CAPACITY) {
            throw new IllegalStateException("Queue size more than expected.");
        }
        if (this.queuelist.size() == this.MAX_CAPACITY) {
            this.queuelist.remove(0);
            this.queuelist.add(t);
        } else {
            this.queuelist.add(t);
        }

        lock.unlock();
        return true;
    }

    /**
     * Inserts the specified element into this queue if it is possible to do so immediately without violating capacity
     *  restrictions. When using a capacity-restricted queue, this method is generally preferable to add(E), which can
     *  fail to insert an element only by throwing an exception.
     * @param t
     * @return
     */
    @Override
    public boolean offer(T t) {
        try {
            return this.add(t);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves and removes the head of this queue. This method differs from poll only in that it throws an exception
     * if this queue is empty.
     * @return
     */
    @Override
    public T remove() throws IllegalStateException {
        lock.lock();
        T t;
        if (this.queuelist.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        } else {
            t = this.queuelist.remove(0);
        }
        lock.unlock();
        return t;
    }

    /**
     * Retrieves and removes the head of this queue, or returns null if this queue is empty.
     * @return
     */
    @Override
    public T poll() {
        try {
            return this.remove();
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public T element() {
        lock.lock();
        T t;
        if (this.queuelist.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        } else {
            t = this.queuelist.get(0);
        }
        lock.unlock();
        return t;
    }

    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     * @return
     */
    @Override
    public T peek() {
        try {
            return this.element();
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
