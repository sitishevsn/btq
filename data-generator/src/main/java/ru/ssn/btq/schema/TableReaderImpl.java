package ru.ssn.btq.schema;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TableReaderImpl implements TableReader {

    @Autowired
    private ColumnFactory columnFactory;

    @Override
    public Table read(String fileName) {
        List<Column> columns = new ArrayList<>();
        String tableName = null;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");

                if (tableName == null) {
                    tableName = extract(values, 0);
                }
                String columnName = extract(values, 1);
                String columnType = extract(values, 2);

                Column column = columnFactory.getColumn(columnName, columnType);
                columns.add(column);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return new Table(tableName, columns);
    }

    private @Nonnull String extract(String[] values, int index) {
        if (index < values.length) {
            String value = values[index];
            return value.toLowerCase().trim();
        } else {
            throw new IllegalArgumentException("The index " + index + " is out of bound. Array length is "
                    + values.length);
        }
    }

}
