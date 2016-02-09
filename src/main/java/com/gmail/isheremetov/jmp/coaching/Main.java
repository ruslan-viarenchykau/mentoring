package com.gmail.isheremetov.jmp.coaching;

import com.gmail.isheremetov.jmp.coaching.utils.RestUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("Time start: " + new Date().getTime());
        List<String> keys = RestUtils.getListFromUrl(RestUtils.HOST + RestUtils.KEYS);
        System.out.println(keys);

        int keysNumber = keys.size();
        ExecutorService service = Executors.newSingleThreadExecutor();
        BlockingQueue<Entry> queue = new ArrayBlockingQueue<>(keysNumber);

        for (String key: keys) {
            new GetStarter(queue, service.submit(new GetStringsThread(key))).start();
        }
        service.shutdown();

        BlockingQueue<Entry> blockingQueue = new ArrayBlockingQueue<>(keysNumber);
        new GetLengthThread(keysNumber, queue, blockingQueue).start();
        new ReportGenerator(keysNumber, blockingQueue).start();
    }

    static class GetStarter extends Thread {
        private BlockingQueue<Entry> queue;
        private Future<Entry> entryFuture;

        public GetStarter(BlockingQueue<Entry> queue, Future<Entry> entryFuture) {
            this.queue = queue;
            this.entryFuture = entryFuture;
        }

        @Override
        public void run() {
            try {
                queue.put(entryFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}
