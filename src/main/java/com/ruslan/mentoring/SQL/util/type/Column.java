package com.ruslan.mentoring.SQL.util.type;

import java.util.Random;

public class Column {
    private static final Random RANDOM = new Random();
    private String name;
    private ColumnType type;

    public Column(String name, ColumnType type) {
        this.name = name;
        this.type = type;
    }

    public static Column getRandom(String namePrefix) {
        String name = namePrefix + RANDOM.nextInt(Integer.MAX_VALUE);
        return new Column(name, ColumnType.getRandom());
    }

    public String getName() {
        return name;
    }

    public ColumnType getType() {
        return type;
    }

    @Override
    public String toString() {
        return name + " " + type.toString();
    }
}
