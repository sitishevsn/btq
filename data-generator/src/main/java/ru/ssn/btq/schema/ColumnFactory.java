package ru.ssn.btq.schema;

public interface ColumnFactory {

    Column getColumn(String name, String type);

}
