package com.ruslan.mentoring.Concurrency.task04;

import java.util.Random;

public class Producer implements Runnable {
    private Bus bus;
    private int iterationsNumber;
    private long timeout;

    public Producer(Bus bus, int iterationsNumber, long timeout) {
        this.bus = bus;
        this.iterationsNumber = iterationsNumber;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < iterationsNumber; i++) {
                String message = String.valueOf(new Random().nextInt());
                System.out.println("Posting " + message);
                bus.post(message);
                System.out.println("Message posted: " + message);
                Thread.sleep(timeout);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
