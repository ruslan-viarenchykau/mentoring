package com.ruslan.mentoring.MemoryManagement.task03;

class SimpleStack {
    private final Object[] objectPool = new Object[10];
    private int pointer = -1;

    public Object pop() {
        if(pointer < 0) {
            throw new IllegalStateException("no elements on stack");
        }
        Object o = objectPool[pointer];
        objectPool[pointer--] = null;
        return o;
    }

    public Object peek() {
        if(pointer < 0) {
            throw new IllegalStateException("no elements on stack");
        }
        return objectPool[pointer];

    }

    public void push(Object object) {
        if(pointer > 8) {
            throw new IllegalStateException("stack overflow");
        }
        objectPool[++pointer] = object;
    }
}

