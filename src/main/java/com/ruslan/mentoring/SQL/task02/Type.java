package com.ruslan.mentoring.SQL.task02;

public enum Type {
    BOOLEAN("Boolean"), INT("Int"), NUMERIC("Numeric"), CHAR("Char"), VARCHAR("Varchar");

    private String name;

    Type(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
