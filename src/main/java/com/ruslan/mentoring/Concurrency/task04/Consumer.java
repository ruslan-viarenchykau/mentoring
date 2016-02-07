package com.ruslan.mentoring.Concurrency.task04;

public class Consumer implements Runnable {
    private Bus bus;
    private int iterationsNumber;
    private long timeout;

    public Consumer(Bus bus, int iterationsNumber, long timeout) {
        this.bus = bus;
        this.iterationsNumber = iterationsNumber;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < iterationsNumber; i++) {
                System.out.println("Getting message..");
                String message = bus.get();
                System.out.println("Message obtained: " + message);
                Thread.sleep(timeout);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
