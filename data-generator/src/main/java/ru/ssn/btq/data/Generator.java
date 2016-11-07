package ru.ssn.btq.data;

import ru.ssn.btq.schema.Row;
import ru.ssn.btq.schema.Table;

import java.util.Iterator;

public interface Generator {

    Iterator<Row> generateFor(Table table);

    Iterator<Row> generateFor(Table table, Integer size);

}
