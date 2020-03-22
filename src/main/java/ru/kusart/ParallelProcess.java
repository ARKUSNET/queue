package ru.kusart;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ParallelProcess {
    int numProducer;
    int numConsumer;
    private Thread[] cThreads;
    private Thread[] pThreads;
    private BlockingQueue<Object> queue = null;

    public ParallelProcess(int numProcuder, int numConsumer, int queueSize) {
        this.numProducer = numProcuder;
        this.numConsumer = numConsumer;
        this.queue = new LinkedBlockingQueue<Object>(queueSize);

        // create consumer thread objects
        cThreads = new Thread[numConsumer];
        for (int i = 0; i < numConsumer; i++) {
            cThreads[i] = new Thread(new Consumer(queue, "CONSUMER"));
        }

        // create producer thread objects
        pThreads = new Thread[numProducer];
        for (int i = 0; i < numProducer; i++) {
            pThreads[i] = new Thread(new Producer(queue, "PRODUCER"));
        }
    }

    public void execute() {

        // start consumer threads
        for (Thread t : cThreads) {
            t.start();
        }

        // start producer threads
        for (Thread t : pThreads) {
            t.start();
        }

        // wait for all producer threads to finish
        for (Thread t : pThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                // this situation is un-handled
                e.printStackTrace();
            }
        }

        // Once the producers are done with their job, we notify check if there
        // is anything left for the consumers to process.
        // If the queue is empty, the the consumer threads should also exit.
        while (true) {
            if (queue.size() > 0) {
                try {
                    // wait 5 more seconds
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                for (Thread t : cThreads) {
                    t.interrupt();
                }
                break;
            }
        }

        // Wait for all the consumers threads to finish as well.
        for (Thread t : cThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}