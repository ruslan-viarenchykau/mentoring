package com.ruslan.mentoring.SQL.util.type;

public enum Type {
    BOOLEAN("Boolean", "bool", false), INT("Int", "int4", false), NUMERIC("Numeric", "numeric", false),
    CHAR("Char", "bpchar", true), VARCHAR("Varchar", "varchar", true);

    private String name;
    private String dataSourceName;
    private boolean sizable;

    Type(String name, String dataSourceName, boolean sizable) {
        this.name = name;
        this.dataSourceName = dataSourceName;
        this.sizable = sizable;
    }

    public static Type fromDataSourceName(String dataSourceName) {
        for (Type type: Type.values()) {
            if (type.dataSourceName.equals(dataSourceName)) {
                return type;
            }
        }
        return null;
    }

    public boolean isSizable() {
        return sizable;
    }

    @Override
    public String toString() {
        return name;
    }
}
