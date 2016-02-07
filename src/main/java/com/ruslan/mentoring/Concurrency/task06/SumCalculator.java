package com.ruslan.mentoring.Concurrency.task06;

import java.util.List;

public class SumCalculator implements Runnable {
    private final List<Integer> integers;

    public SumCalculator(List<Integer> integers) {
        this.integers = integers;
    }

    @Override
    public void run() {
        while (true) {
            int sum = 0;
            for (int value : integers) {
                sum += value;
            }
            System.out.println("Calculated sum: " + sum);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
