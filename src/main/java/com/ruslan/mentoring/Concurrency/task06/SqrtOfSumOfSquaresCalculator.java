package com.ruslan.mentoring.Concurrency.task06;

import java.util.List;

public class SqrtOfSumOfSquaresCalculator implements Runnable {
    private final List<Integer> integers;

    public SqrtOfSumOfSquaresCalculator(List<Integer> integers) {
        this.integers = integers;
    }

    @Override
    public void run() {
        while (true) {
            int sumOfSquares = 0;
            for (int value : integers) {
                sumOfSquares += value * value;
            }
            System.out.println("Calculated square root of sum of squares: " + Math.sqrt(sumOfSquares));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
