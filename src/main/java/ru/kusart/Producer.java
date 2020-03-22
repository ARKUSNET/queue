package ru.kusart;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Producer extends Thread {

    private BlockingQueue<Object> queue;
    private Random rg = new Random();
    private static int count = 0;

    public Producer(BlockingQueue<Object> queue, String name) {
        super(name);
        this.queue = queue;
    }

    @Override
    public void run() {
        synchronized (queue) {

            String item = "Item" + count;
            System.out.println("Producer: " + Thread.currentThread().getName()
                    + " adding " + item + " to the queue");
            count++;
            boolean success = queue.offer(item);
            while (!success) {
                success = queue.offer(item);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // continue again
                }
            }
        }
        try {
            long sleepTime = Math.abs(rg.nextLong()) % 500;
            System.out.println("Producer: " + Thread.currentThread().getName()
                    + " sleeping for " + sleepTime + "ms");
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
