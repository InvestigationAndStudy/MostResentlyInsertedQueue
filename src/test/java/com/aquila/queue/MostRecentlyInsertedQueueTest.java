package com.aquila.queue;


import org.junit.Assert;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MostRecentlyInsertedQueueTest {
    @Test
    public void testMostRecentlyInsertedQueueTestInspections() {

        Queue<Integer> queue = new MostRecentlyInsertedQueue<Integer>(3);
        Assert.assertEquals(
            this.inspect(queue),
            "queue.size(): 0, contents (head -> tail): [  ]"
        );
        queue.offer(1);
        Assert.assertEquals(
            this.inspect(queue),
            "queue.size(): 1, contents (head -> tail): [ 1 ]"
        );
        queue.offer(2);
        Assert.assertEquals(
            this.inspect(queue),
            "queue.size(): 2, contents (head -> tail): [ 1, 2 ]"
        );
        queue.offer(3);
        Assert.assertEquals(
            this.inspect(queue),
            "queue.size(): 3, contents (head -> tail): [ 1, 2, 3 ]"
        );
        queue.offer(4);
        Assert.assertEquals(
            this.inspect(queue),
            "queue.size(): 3, contents (head -> tail): [ 2, 3, 4 ]"
        );
        queue.offer(5);
        Assert.assertEquals(
            this.inspect(queue),
            "queue.size(): 3, contents (head -> tail): [ 3, 4, 5 ]"
        );
        Integer poll1 = queue.poll();
        Assert.assertEquals(
            this.inspect(queue).concat(", poll1 = ").concat(poll1.toString()),
            "queue.size(): 2, contents (head -> tail): [ 4, 5 ], poll1 = 3"
        );
        Integer poll2 = queue.poll();
        Assert.assertEquals(
            this.inspect(queue).concat(", poll2 = ").concat(poll2.toString()),
            "queue.size(): 1, contents (head -> tail): [ 5 ], poll2 = 4"
        );
        queue.clear();
        Assert.assertEquals(
            this.inspect(queue),
            "queue.size(): 0, contents (head -> tail): [  ]"
        );

    }

    @Test
    public void testConcurrentMostRecentlyInsertedQueue () {
        Queue<String> queue = new ConcurrentMostRecentlyInsertedQueue<String>(10);

        IntStream.range(0, 100).parallel().forEach(i -> {
            System.out.println("Queue insertion: a count: " + i);
            queue.add("a");
        });
        IntStream.range(0, 5).parallel().forEach(i -> {
            System.out.println("Queue removing: count: " + i);
            queue.poll();
        });
        Assert.assertEquals(
            this.inspect(queue),
            "queue.size(): 5, contents (head -> tail): [ a, a, a, a, a ]"
        );
    }

    @Test
    public void testMostRecentlyInsertedQueueLock() {
        Queue<Integer> queue = new MostRecentlyInsertedQueueLock<Integer>(3);
        Assert.assertEquals(
            this.inspect(queue),
            "queue.size(): 0, contents (head -> tail): [  ]"
        );
        queue.offer(1);
        Assert.assertEquals(
            this.inspect(queue),
            "queue.size(): 1, contents (head -> tail): [ 1 ]"
        );
        queue.offer(2);
        Assert.assertEquals(
            this.inspect(queue),
            "queue.size(): 2, contents (head -> tail): [ 1, 2 ]"
        );
        queue.offer(3);
        Assert.assertEquals(
            this.inspect(queue),
            "queue.size(): 3, contents (head -> tail): [ 1, 2, 3 ]"
        );
        queue.offer(4);
        Assert.assertEquals(
            this.inspect(queue),
            "queue.size(): 3, contents (head -> tail): [ 2, 3, 4 ]"
        );
        queue.offer(5);
        Assert.assertEquals(
            this.inspect(queue),
            "queue.size(): 3, contents (head -> tail): [ 3, 4, 5 ]"
        );
        Integer poll1 = queue.poll();
        Assert.assertEquals(
            this.inspect(queue).concat(", poll1 = ").concat(poll1.toString()),
            "queue.size(): 2, contents (head -> tail): [ 4, 5 ], poll1 = 3"
        );
        Integer poll2 = queue.poll();
        Assert.assertEquals(
            this.inspect(queue).concat(", poll2 = ").concat(poll2.toString()),
            "queue.size(): 1, contents (head -> tail): [ 5 ], poll2 = 4"
        );
        queue.clear();
        Assert.assertEquals(
            this.inspect(queue),
            "queue.size(): 0, contents (head -> tail): [  ]"
        );

    }

    @Test
    public void testMostRecentlyInsertedBlockingQueueOfferPoll() {
        BlockingQueue<Integer> bq = new MostRecentlyInsertedBlockingQueue<>(3);
        Assert.assertEquals(bq.size(), 0);
        bq.offer(1);
        bq.offer(2);
        bq.offer(3);
        bq.offer(4);
        Assert.assertEquals(bq.size(),3);
        Assert.assertEquals((int)bq.poll(), 1);
        Assert.assertEquals(bq.size(),2);
        Assert.assertEquals((int)bq.poll(), 2);
        Assert.assertEquals((int)bq.poll(), 3);
        Assert.assertEquals(bq.size(),0);

    }

    @Test
    public void testMostRecentlyInsertedBlockingQueuePutPoll() throws InterruptedException {
        BlockingQueue<Integer> bq = new MostRecentlyInsertedBlockingQueue<>(3);
        Assert.assertEquals(bq.size(), 0);
        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("Before insert [1]");
                    bq.put(1);
                    System.out.println("Before insert [2]");
                    bq.put(2);
                    System.out.println("Before insert [3]");
                    bq.put(3);
                    System.out.println("Before insert [4]");
                    bq.put(4);
                    System.out.println("Inserted all");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        Thread.sleep(1000);

        Assert.assertEquals(bq.size(), 3);
        Assert.assertEquals((int)bq.poll(), 1);
        Thread.sleep(1000);
        System.out.println(bq.toString());
        Assert.assertEquals(bq.size(), 3);
        Assert.assertEquals((int)bq.peek(), 2);
        Assert.assertEquals((int)bq.peek(), 2);
        Assert.assertEquals(bq.size(), 3);
    }


    private String inspect(final Queue<?> queue) {
        return String.format(
            "queue.size(): %s, contents (head -> tail): [ %s ]",
            queue.size(),
            queue.stream().map(Object::toString).collect(Collectors.joining(", "))
        );
    }



}
