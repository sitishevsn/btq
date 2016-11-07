package ru.ssn.btq.store;

import ru.ssn.btq.schema.Row;
import ru.ssn.btq.schema.Table;

import java.util.Iterator;

public interface Store {

    void store(Table table, Iterator<Row> data);

}
