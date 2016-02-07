package com.ruslan.mentoring.Concurrency.task03;

public class Resource {
    private volatile int counter = 10;

    public synchronized int increase() {

        while (counter == 10) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int value = ++ counter;
        System.out.println("Resource: increased up to " + value);
        notifyAll();
        return value;
    }

    public synchronized int decrease() {
        while (counter == 5) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int value =  -- counter;
        System.out.println("Resource: decreased down to " + value);
        notifyAll();
        return value;
    }


}
