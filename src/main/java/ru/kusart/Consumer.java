package ru.kusart;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer extends Thread {

    private BlockingQueue<Object> queue;
    private Random rg = new Random();

    public Consumer(BlockingQueue<Object> queue, String name) {
        super(name);
        this.queue = queue;
    }

    @Override
    public void run() {
        boolean more = true;
        while (more) {
            String item = null;
            try {
                item = (String) queue.take();
            } catch (InterruptedException e) {
                System.out.println("Consumer: "
                        + Thread.currentThread().getName()
                        + " interrupted rcvd");
                break;
            }
            long sleepTime = Math.abs(rg.nextLong()) % 500;
            System.out.println("Consumer: " + Thread.currentThread().getName()
                    + " sleeping for " + sleepTime + "ms");
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                more = false;
            }

            System.out.println("Consumer: " + Thread.currentThread().getName()
                    + " received " + item + " from the queue");
        }
    }
}