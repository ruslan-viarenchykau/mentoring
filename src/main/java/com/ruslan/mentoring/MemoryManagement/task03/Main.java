package com.ruslan.mentoring.MemoryManagement.task03;

public class Main {

    public static void main(String[] args) {
        while (true) {
            SimpleStack simpleStack = new SimpleStack();
            simpleStack.push(simpleStack);
            for (int i = 1; i < 8; i++) {
                simpleStack.push(new Object());
            }
            for (int i = 0; i < 8; i++) {
                simpleStack.pop();
            }
        }
    }
}
