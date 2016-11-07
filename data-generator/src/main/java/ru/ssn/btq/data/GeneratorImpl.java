package ru.ssn.btq.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import ru.ssn.btq.Properties;
import ru.ssn.btq.rnd.FakeValues;
import ru.ssn.btq.schema.Column;
import ru.ssn.btq.schema.Row;
import ru.ssn.btq.schema.Table;

import java.util.Iterator;
import java.util.List;

public class GeneratorImpl implements Generator {

    @Autowired
    private FakeValues fakeValues;

    @Autowired
    private Environment environment;

    @Override
    public Iterator<Row> generateFor(Table table) {
        return generateFor(table, null);
    }

    @Override
    public Iterator<Row> generateFor(Table table, Integer size) {
        List<Column> columns = table.getColumns();
        ObjectColumnVisitor visitor = new ObjectColumnVisitor(fakeValues);
        int dataLength;
        if (size != null) {
            dataLength = size;
        } else {
            dataLength = getDataLength();
        }

        return new Iterator<Row>() {
            private int counter;

            @Override
            public boolean hasNext() {
                return counter++ < dataLength;
            }

            @Override
            public Row next() {
                Object[] values = new Object[columns.size()];
                int index = 0;
                for (Column column : columns) {
                    values[index++] = column.accept(visitor);
                }

                return new Row(values);
            }
        };
    }

    private int getDataLength() {
        String strValue = environment.getProperty(Properties.DATA_LENGTH_KEY, Properties.DEFAULT_DATA_LENGTH_VALUE);
        int value;
        try {
            value = Integer.parseInt(strValue);
        } catch (NumberFormatException e) {
            value = Integer.parseInt(Properties.DEFAULT_DATA_LENGTH_VALUE);
        }

        return value;
    }
}
