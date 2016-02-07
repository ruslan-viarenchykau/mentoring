package com.ruslan.mentoring.Concurrency.task04;

public class Main {
    private static final int INSTANCES_NUMBER = 10;
    private static final int ITERATIONS_NUMBER = 5;

    public static void main(String[] args) throws InterruptedException {
        Bus bus = new Bus();

        int timeout;
        Thread producerThread, consumerThread;
        for (int i = 0; i < INSTANCES_NUMBER; i++) {
            timeout = i *200;

            producerThread = new Thread(new Producer(bus, ITERATIONS_NUMBER, timeout));
            producerThread.start();

            consumerThread = new Thread(new Consumer(bus, ITERATIONS_NUMBER, timeout));
            consumerThread.start();
        }
    }
}
