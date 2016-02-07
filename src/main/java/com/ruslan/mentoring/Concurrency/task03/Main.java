package com.ruslan.mentoring.Concurrency.task03;

public class Main {
    private static final int ITERATIONS_NUMBER = 20;

    public static void main(String[] args) throws InterruptedException {
        Resource resource = new Resource();
        Producer producer = new Producer(resource, ITERATIONS_NUMBER, 200);
        Consumer consumer = new Consumer(resource, ITERATIONS_NUMBER, 150);

        System.out.println("Main: Starting action..");

        Thread pThread = new Thread(producer);
        pThread.start();
        Thread cThread = new Thread(consumer);
        cThread.start();

        System.out.println("Main: The action has been started");

        pThread.join();
        cThread.join();

        System.out.println("Main: The action has been completed");
    }
}
