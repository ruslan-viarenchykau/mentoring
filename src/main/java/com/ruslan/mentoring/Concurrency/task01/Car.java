package com.ruslan.mentoring.Concurrency.task01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Car implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(Car.class);

    private static final long MAX_DISTANCE = 10000;

    private long friction;
    private long distance;
    private String name;

    public Car(String name, long friction) {
        this.name = name;
        this.friction = friction;
    }

    @Override
    public void run() {
        try {
            while (distance < MAX_DISTANCE) {
                if (Thread.currentThread().isInterrupted()) {
                    Main.handleDisqualified(name);
                    return;
                }
                Thread.sleep(friction);
                distance += 100;
                LOG.info("{} {}", name, distance);
            }
            Main.handleFinished(name);
        } catch (InterruptedException e) {
            Main.handleDisqualified(name);
        }

    }
}