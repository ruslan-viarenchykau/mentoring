package com.ruslan.mentoring.SQL.util.type;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class ColumnType {
    private static final Random RANDOM = new Random();
    private Type type;
    private int size;

    public ColumnType(Type type) {
        this.type = type;
        this.size = 0;
    }

    public String getRandomValue() {
        String value = null;
        switch (this.type) {
            case BOOLEAN:
                value = String.valueOf(RANDOM.nextBoolean());
                break;
            case INT:
                value = String.valueOf(RANDOM.nextInt());
                break;
            case NUMERIC:
                value = String.valueOf(RANDOM.nextFloat());
                break;
            case CHAR:
                value = RandomStringUtils.randomAlphabetic(size);
                break;
            case VARCHAR:
                value = RandomStringUtils.randomAlphabetic(RANDOM.nextInt(size));
                break;
        }
        return value;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static ColumnType getRandom() {
        Type type = Type.values()[RANDOM.nextInt(Type.values().length)];
        ColumnType columnType = new ColumnType(type);
        switch (type) {
            case CHAR:
                columnType.setSize(RANDOM.nextInt(200) + 1);
                break;
            case VARCHAR:
                columnType.setSize(RANDOM.nextInt(500) + 1);
                break;
        }
        return columnType;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        if (size > 0) {
            return type.toString() + "(" + size + ")";
        } else {
            return type.toString();
        }
    }
}
