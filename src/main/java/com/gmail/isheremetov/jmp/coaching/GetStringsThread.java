package com.gmail.isheremetov.jmp.coaching;

import com.gmail.isheremetov.jmp.coaching.utils.RestUtils;

import java.util.concurrent.Callable;

public class GetStringsThread implements Callable<Entry> {

    private String key;

    public GetStringsThread(String key) {
        this.key = key;
    }

    @Override
    public Entry call() throws Exception {
        System.out.println("Return list for key " + key);
        return new Entry(key, RestUtils.getListFromUrl(RestUtils.HOST + RestUtils.KEYS + key));
    }

}