package com.ruslan.mentoring.MemoryManagement.task01;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        while(true) {
            list.add(new A());
        }
    }

    static class A {
        private Object[] objects = new Object[1000];
    }
}
