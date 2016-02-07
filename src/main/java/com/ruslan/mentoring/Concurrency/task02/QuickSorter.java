package com.ruslan.mentoring.Concurrency.task02;

public class QuickSorter {
    private int []a;

    public QuickSorter(int[] a) {
        this.a = a;
    }

    // This method sort an array internally it calls to quickSort
    public int[] sort(){
        int left = 0;
        int right = a.length-1;

        quickSort(left, right);
        return a;
    }

    // This method is used to sort the array using quicksort algorithm.
    // It takes left and the right end of the array as two cursors.
    private void quickSort(int left,int right){

        // If both cursor scanned the complete array quicksort exits
        if(left >= right)
            return;

        // For the simplicity, we took the right most item of the array as a pivot
        int pivot = a[right];
        int partition = partition(left, right, pivot);

        // Recursively, calls the quicksort with the different left and right parameters of the sub-array
        quickSort(0, partition-1);
        quickSort(partition+1, right);
    }

    // This method is used to partition the given array and returns the integer which points to the sorted pivot index
    private int partition(int left,int right,int pivot){
        int leftCursor = left-1;
        int rightCursor = right;
        while(leftCursor < rightCursor){
            while(a[++leftCursor] < pivot);
            while(rightCursor > 0 && a[--rightCursor] > pivot);
            if(leftCursor >= rightCursor){
                break;
            }else{
                swap(leftCursor, rightCursor);
            }
        }
        swap(leftCursor, right);
        return leftCursor;
    }

    // This method is used to swap the values between the two given index
    public void swap(int left,int right){
        int temp = a[left];
        a[left] = a[right];
        a[right] = temp;
    }
}
