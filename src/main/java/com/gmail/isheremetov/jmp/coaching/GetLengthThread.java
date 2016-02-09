package com.gmail.isheremetov.jmp.coaching;

import com.gmail.isheremetov.jmp.coaching.utils.RestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class GetLengthThread extends Thread {
    private int keysNumber;
    private BlockingQueue<Entry> blockingQueue;
    private BlockingQueue<Entry> blockingQueue2;

    public GetLengthThread(int keysNumber, BlockingQueue<Entry> blockingQueue, BlockingQueue<Entry> blockingQueue2) {
        this.keysNumber = keysNumber;
        this.blockingQueue = blockingQueue;
        this.blockingQueue2 = blockingQueue2;
    }

    @Override
    public void run() {
        Entry entry;
        String length;
        List<String> lengths;

        try {
            while (keysNumber-- > 0) {
                entry = blockingQueue.take();
                lengths = new ArrayList<>();
                for (String s: entry.getValue()) {
                    length = RestUtils.postStringToUrl(RestUtils.HOST + RestUtils.LENGTH, s);
                    lengths.add(length);
                }

                blockingQueue2.put(new Entry(entry.getKey(), lengths));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}