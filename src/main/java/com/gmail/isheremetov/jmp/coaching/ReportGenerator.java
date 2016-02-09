package com.gmail.isheremetov.jmp.coaching;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ReportGenerator extends Thread {
    private int keysNumber;
    private BlockingQueue<Entry> blockingQueue;
    private List<Entry> entryList = new ArrayList<>();

    public ReportGenerator(int keysNumber, BlockingQueue<Entry> blockingQueue) {
        this.keysNumber = keysNumber;
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (keysNumber-- > 0) {
            try {
                Entry e = blockingQueue.take();
                entryList.add(e);
                generateReport1(e);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }

        generateReport2(entryList);
        System.out.println("Time end: " + new Date().getTime());
    }

    private void generateReport1(Entry entry) {
        System.out.println(entry.toString());
    }

    private void generateReport2(List<Entry> entries) {
        int length = 0;

        for (Entry e: entries) {
            for (String s: e.getValue()) {
                length += Integer.parseInt(s);
            }
        }

        System.out.println("Total = " + length);
    }
}