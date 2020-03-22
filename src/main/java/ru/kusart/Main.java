package ru.kusart;

import java.util.LinkedList;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
        // provide number of producers, number of consumers and the
        // max-queue-length
        ParallelProcess process = new ParallelProcess(10, 1, 4);
        process.execute();
        System.out.println("Thread: " + Thread.currentThread().getName()
                + " FINISHED");
    }
}
