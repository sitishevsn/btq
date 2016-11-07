package ru.ssn.btq.schema;

public class Row {

    private final Object[] values;

    public Row(Object[] values) {
        this.values = values;
    }

    public Object[] getValues() {
        return values;
    }

}
