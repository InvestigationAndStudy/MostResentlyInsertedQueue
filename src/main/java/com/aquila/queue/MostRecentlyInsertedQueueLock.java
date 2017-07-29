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
        try {
            s = this.queuelist.size();
        } finally {
            lock.unlock();
        }
        return s;
    }

    @Override
    public boolean isEmpty() {
        lock.lock();
        boolean b;
        try {
            b = this.queuelist.isEmpty();
        } finally {
            lock.unlock();
        }
        return b;
    }

    @Override
    public boolean contains(Object o) {
        lock.lock();
        boolean b;
        try {
            b = this.queuelist.contains(o);
        } finally {
            lock.unlock();
        }
        return b;
    }

    @Override
    public Iterator<T> iterator() {
        lock.lock();
        Iterator<T> i;
        try {
            i = this.queuelist.iterator();
        } finally {
            lock.unlock();
        }
        return i;
    }

    @Override
    public Object[] toArray() {
        lock.lock();
        Object[] o;
        try {
            o = this.queuelist.toArray();
        } finally {
            lock.unlock();
        }
        return o;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        lock.lock();
        T1[] t;
        try {
            t = this.queuelist.toArray(a);
        } finally {
            lock.unlock();
        }
        return t;
    }

    @Override
    public boolean remove(Object o) {
        lock.lock();
        boolean b;
        try {
            b = this.queuelist.remove(o);
        } finally {
            lock.unlock();
        }
        return b;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        lock.lock();
        boolean b;
        try {
            b = this.queuelist.containsAll(c);
        } finally {
            lock.unlock();
        }
        return b;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        lock.lock();
        boolean b;
        try {
            b = this.queuelist.addAll(c);
        } finally {
            lock.unlock();
        }
        return b;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        lock.lock();
        boolean b;
        try {
            b = this.queuelist.removeAll(c);
        } finally {
            lock.unlock();
        }
        return b;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        lock.lock();
        boolean b;
        try {
            b = this.queuelist.retainAll(c);
        } finally {
            lock.unlock();
        }
        return b;
    }

    @Override
    public void clear() {
        lock.lock();
        try {
            this.queuelist.clear();
        } finally {
            lock.unlock();
        }
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
        try {
            if (this.queuelist.size() > this.MAX_CAPACITY) {
                throw new IllegalStateException("Queue size more than expected.");
            }
            if (this.queuelist.size() == this.MAX_CAPACITY) {
                this.queuelist.remove(0);
                this.queuelist.add(t);
            } else {
                this.queuelist.add(t);
            }
        } finally {
            lock.unlock();
        }
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
        try {
            if (this.queuelist.isEmpty()) {
                throw new IllegalStateException("Queue is empty");
            } else {
                t = this.queuelist.remove(0);
            }
        } finally {
            lock.unlock();
        }
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
        try {
            if (this.queuelist.isEmpty()) {
                throw new IllegalStateException("Queue is empty");
            } else {
                t = this.queuelist.get(0);
            }
        } finally {
            lock.unlock();
        }
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
