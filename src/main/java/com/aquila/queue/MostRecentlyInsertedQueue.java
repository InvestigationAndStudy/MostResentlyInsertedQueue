package com.aquila.queue;

import java.util.*;

public class MostRecentlyInsertedQueue<T> implements Queue<T> {
    private final List<T> queuelist;
    private final int MAX_CAPACITY;

    public MostRecentlyInsertedQueue(final int capacity) {
        this.MAX_CAPACITY = capacity;
        this.queuelist = new ArrayList<T>(this.MAX_CAPACITY);
    }


    @Override
    public int size() {
        synchronized (this.queuelist) {
            return this.queuelist.size();
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (this.queuelist) {
            return this.queuelist.isEmpty();
        }
    }

    @Override
    public boolean contains(Object o) {
        synchronized (this.queuelist) {
            return this.queuelist.contains(o);
        }
    }

    @Override
    public Iterator<T> iterator() {
        synchronized (this.queuelist) {
            return this.queuelist.iterator();
        }
    }

    @Override
    public Object[] toArray() {
        synchronized (this.queuelist) {
            return this.queuelist.toArray();
        }
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        synchronized (this.queuelist) {
            return this.queuelist.toArray(a);
        }
    }

    @Override
    public boolean remove(Object o) {
        synchronized (this.queuelist) {
            return this.queuelist.remove(o);
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        synchronized (this.queuelist) {
            return this.queuelist.containsAll(c);
        }
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        synchronized (this.queuelist) {
            return this.queuelist.addAll(c);
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        synchronized (this.queuelist) {
            return this.queuelist.removeAll(c);
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        synchronized (this.queuelist) {
            return this.queuelist.retainAll(c);
        }
    }

    @Override
    public void clear() {
        synchronized (this.queuelist) {
            this.queuelist.clear();
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
        synchronized (this.queuelist) {
            if (this.queuelist.size() > this.MAX_CAPACITY) {
                throw new IllegalStateException("Queue size more than expected.");
            }
            if (this.queuelist.size() == this.MAX_CAPACITY) {
                this.queuelist.remove(0);
                this.queuelist.add(t);
            } else {
                this.queuelist.add(t);
            }
            return true;
        }
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
        synchronized (this.queuelist) {
            if (this.queuelist.isEmpty()) {
                throw new IllegalStateException("Queue is empty");
            } else {
                return this.queuelist.remove(0);
            }
        }
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
        synchronized (this.queuelist) {
            if (this.queuelist.isEmpty()) {
                throw new IllegalStateException("Queue is empty");
            } else {
                return this.queuelist.get(0);
            }
        }
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
