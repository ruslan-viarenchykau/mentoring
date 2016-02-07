package com.ruslan.mentoring.Concurrency.task02;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class QuickSortTask extends RecursiveTask<int[]> {
    private int[] array;
    private int threshold;

    public QuickSortTask(int[] array, int threshold) {
        this.array = array;
        this.threshold = threshold;
    }

    @Override
    protected int[] compute() {
        if (array.length <= threshold) {
            return new QuickSorter(array).sort();
        } else {
            int split = array.length / 2;

            RecursiveTask<int[]> part1 = new QuickSortTask(Arrays.copyOf(array, split), threshold);
            RecursiveTask<int[]> part2 = new QuickSortTask(Arrays.copyOfRange(array, split, array.length), threshold);
            part1.fork();
            part2.fork();
            return Main.mergeSortedArrays(part1.join(), part2.join());
        }
    }
}
