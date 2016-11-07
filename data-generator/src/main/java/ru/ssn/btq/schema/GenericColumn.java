package ru.ssn.btq.schema;

public abstract class GenericColumn implements Column {

    private final String name;

    public GenericColumn(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                '}';
    }
}
