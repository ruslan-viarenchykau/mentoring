package com.ruslan.mentoring.GarbageCollection.task01;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);
        List<StringBuilder> stringBuilders;
        StringBuilder stringBuilder;
        while (true) {
            stringBuilders = new ArrayList<>();
            for (int i = 0; i < 4000; i++) {
                stringBuilder = new StringBuilder();
                for (int j = 0; j < 60; j++) {
                    // build big object
                    stringBuilder.append(String.valueOf(Math.random()));
                }
                // add big object to list
                stringBuilders.add(stringBuilder);
            }
        }
    }
}
