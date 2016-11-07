package ru.ssn.btq.store;

import com.google.common.base.Joiner;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import ru.ssn.btq.Properties;
import ru.ssn.btq.schema.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class StoreImpl implements Store {

    public static final Logger LOGGER = LoggerFactory.getLogger(Store.class);

    @Autowired
    private Environment env;

    @Autowired
    private FileSystem fileSystem;

    @Override
    public void store(Table table, Iterator<Row> data) {
        String tableName = table.getName();
        LOGGER.info("Start storing table {}", tableName);

        int perPartitionSize = getPerPartitionSize();
        int partition = 0;
        int counter = 0;
        PrintWriter writer = getPrintWriter(tableName, partition++);
        while (data.hasNext()) {
            if (writer == null) {
                writer = getPrintWriter(tableName, partition++);
            }

            Row next = data.next();
            storeRow(next, table.getColumns(), writer);
            counter++;

            if (counter >= perPartitionSize) {
                writer.close();
                writer = null;
                LOGGER.info("Partition {} for table {} is done", partition, tableName);
                counter = 0;
            }
        }

        if (counter != 0) {
            writer.close();
        }
        LOGGER.info("Storing table {} is done", tableName);
    }

    private PrintWriter getPrintWriter(String tableName, int partition) {
        Path filePath = getOutputFile(tableName, partition);
        try {
            FSDataOutputStream outputStream = fileSystem.create(filePath);
            return new PrintWriter(outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error creating  file " + filePath, e);
        }
    }

    private void storeRow(Row row, List<Column> columns, PrintWriter writer) {

        Object[] values = row.getValues();
        String[] strValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {

            final Object objVal = values[i];
            final Column column = columns.get(i);
            strValues[i] = column.accept(new Column.Visitor<String>() {

                private final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
                private final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

                @Override
                public String visit(StringColumn column) {
                    return objVal.toString();
                }

                @Override
                public String visit(BigIntegerColumn column) {
                    return objVal.toString();
                }

                @Override
                public String visit(BigDecimalColumn column) {
                    return objVal.toString();
                }

                @Override
                public String visit(DateColumn column) {
                    return DATE_FORMAT.format((Date) objVal);
                }

                @Override
                public String visit(TimestampColumn column) {
                    return TIMESTAMP_FORMAT.format((Date) objVal);
                }

                @Override
                public String visit(CharColumn column) {
                    return objVal.toString();
                }

                @Override
                public String visit(SequenceColumn column) {
                    return objVal.toString();
                }

                @Override
                public String visit(PhoneNumberColumn column) {
                    return objVal.toString();
                }
            });


        }

        String join = Joiner.on(";").join(strValues);
        writer.println(join);
    }

    private Path getOutputFile(String tableName, int partition) {
        String outputPath = env.getProperty(Properties.OUTPUT_PATH_KEY, Properties.DEFAULT_OUTPUT_PATH_VALUE);

        String sb = outputPath + "/" + tableName + "/" +
                String.format("%08d", partition) + ".csv";

        return new Path(sb);
    }

    private int getPerPartitionSize() {
        String size = env.getProperty(Properties.PER_PARTITION_SIZE_KEY, Properties.DEFAULT_PER_PARTITION_SIZE);
        try {
            return Integer.parseInt(size);
        } catch (NumberFormatException e) {
            return Integer.parseInt(Properties.DEFAULT_PER_PARTITION_SIZE);
        }
    }
}
