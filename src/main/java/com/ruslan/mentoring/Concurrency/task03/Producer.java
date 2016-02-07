package com.ruslan.mentoring.Concurrency.task03;

public class Producer implements Runnable {
    private Resource resource;
    private int iterationsNumber;
    private long timeout;

    public Producer(Resource resource, int iterationsNumber, long timeout) {
        this.resource = resource;
        this.iterationsNumber = iterationsNumber;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        System.out.println("Producer: started");
        try {
            for (int i = 0; i < iterationsNumber; i++) {
                System.out.println("Producer: increasing..");
                resource.increase();
                Thread.sleep(timeout);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Producer: finished");
        }
    }
}
