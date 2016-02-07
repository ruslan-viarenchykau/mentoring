package com.ruslan.mentoring.Concurrency.task03;

public class Consumer implements Runnable {
    private Resource resource;
    private int iterationsNumber;
    private long timeout;

    public Consumer(Resource resource, int iterationsNumber, long timeout) {
        this.resource = resource;
        this.iterationsNumber = iterationsNumber;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        System.out.println("Consumer: started");
        try {
            for (int i = 0; i < iterationsNumber; i++) {
                System.out.println("Consumer: decreasing..");
                resource.decrease();
                Thread.sleep(timeout);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Consumer: finished");
        }
    }
}
