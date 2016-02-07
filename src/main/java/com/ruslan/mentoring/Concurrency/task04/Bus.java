package com.ruslan.mentoring.Concurrency.task04;

public class Bus {
    private volatile String message;
    private boolean hasMessage = false;

    public synchronized void post(String message) {

        while (hasMessage) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.message = message;
        hasMessage = true;
        notifyAll();
    }

    public synchronized String get() {
        while (!hasMessage) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        hasMessage = false;
        notifyAll();
        return message;
    }


}
