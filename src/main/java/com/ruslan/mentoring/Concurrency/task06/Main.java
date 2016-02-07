package com.ruslan.mentoring.Concurrency.task06;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    public static void main(String[] args) {
        List<Integer> integers = new CopyOnWriteArrayList<>();

        new Thread(new Generator(integers)).start();
        new Thread(new SumCalculator(integers)).start();
        new Thread(new SqrtOfSumOfSquaresCalculator(integers)).start();
    }
}
