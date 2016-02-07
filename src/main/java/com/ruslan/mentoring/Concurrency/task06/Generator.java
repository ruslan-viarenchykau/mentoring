package com.ruslan.mentoring.Concurrency.task06;

import java.util.List;
import java.util.Random;

public class Generator implements Runnable {

    private Random random = new Random();
    private final List<Integer> integers;

    public Generator(List<Integer> integers) {
        this.integers = integers;
    }

    @Override
    public void run() {
        while (true) {
            int randomInt = random.nextInt(200);
            integers.add(randomInt);
            System.out.println("Added " + randomInt);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
