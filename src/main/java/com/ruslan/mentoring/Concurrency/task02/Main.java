package com.ruslan.mentoring.Concurrency.task02;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final int THRESHOLD = 200;

    public static void main(String[] args) {
        int[] array1 = createArray(5000);
        int[] array2 = Arrays.copyOf(array1, array1.length);

        int[] result1 = generalQuickSort(array1);
        int[] result2 = forkJoinQuickSort(array2);

        System.out.println("Verification passed: " + (isSortedAscending(result1) && Arrays.equals(result1, result2)));
    }

    private static boolean isSortedAscending(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private static int[] generalQuickSort(int[] array) {
        long startTime = new Date().getTime();
        QuickSorter sorter = new QuickSorter(array);
        int[] result = sorter.sort();
        long endTime = new Date().getTime();
        System.out.println("General quick sort finished at " + (endTime - startTime) + " ms.");
        return result;
    }

    private static int[] forkJoinQuickSort(int[] array) {
        long startTime = new Date().getTime(), endTime;
        ForkJoinPool pool = new ForkJoinPool();
        int[] result;
        try {
            result = pool.invoke(new QuickSortTask(array, THRESHOLD));
            endTime = new Date().getTime();
        } finally {
            pool.shutdown();
        }
        System.out.println("ForkJoin quick sort finished at " + (endTime - startTime) + " ms.");
        return result;
    }

    private static int[] createArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = new Random().nextInt();
        }
        return array;
    }

    public static int[] mergeSortedArrays(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) {
                result[k] = a[i];
                i++;
            } else {
                result[k] = b[j];
                j++;
            }
            k++;
        }
        while (i < a.length) {
            result[k] = a[i];
            i++;
            k++;
        }
        while (j < b.length) {
            result[k] = b[j];
            j++;
            k++;
        }
        return result;
    }
}
