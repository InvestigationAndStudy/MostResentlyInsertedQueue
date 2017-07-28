package com.aquila.queue;


public class ConcurrentMostRecentlyInsertedQueue<T> extends MostRecentlyInsertedQueue<T> {
    public ConcurrentMostRecentlyInsertedQueue(int capacity) {
        super(capacity);
    }
}
