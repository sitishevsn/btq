package ru.ssn.btq.store;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import ru.ssn.btq.Properties;
import ru.ssn.btq.data.ColumnNameVisitor;
import ru.ssn.btq.schema.BigDecimalColumn;
import ru.ssn.btq.schema.BigIntegerColumn;
import ru.ssn.btq.schema.CharColumn;
import ru.ssn.btq.schema.Column;
import ru.ssn.btq.schema.DateColumn;
import ru.ssn.btq.schema.PhoneNumberColumn;
import ru.ssn.btq.schema.Row;
import ru.ssn.btq.schema.SequenceColumn;
import ru.ssn.btq.schema.StringColumn;
import ru.ssn.btq.schema.Table;
import ru.ssn.btq.schema.TimestampColumn;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExcelStoreImpl implements Store {

    public static final Logger LOGGER = LoggerFactory.getLogger(ExcelStoreImpl.class);

    @Autowired
    private Environment env;

    @Autowired
    private FileSystem fileSystem;

    @Override
    public void store(Table table, Iterator<Row> data) {
        String tableName = table.getName();
        LOGGER.info("Start storing table {}", tableName);

        SXSSFWorkbook wb = new SXSSFWorkbook();
        wb.setCompressTempFiles(true);
        SXSSFSheet sh = wb.createSheet();
        sh.setRandomAccessWindowSize(100);// keep 100 rows in memory, exceeding rows will be flushed to disk

        int rownum = 0;

        org.apache.poi.ss.usermodel.Row row = sh.createRow(rownum++);
        writeHeader(table, row);

        while (data.hasNext()) {
            Row next = data.next();
            org.apache.poi.ss.usermodel.Row excelRow = sh.createRow(rownum++);
            storeRow(next, table.getColumns(), excelRow);
        }

        try {
            OutputStream out = getOutputStream(tableName);
            wb.write(out);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Cannot write results to xlsx file");
        }


        LOGGER.info("Storing table {} is done", tableName);
    }

    private void writeHeader(Table table, org.apache.poi.ss.usermodel.Row row) {
        ColumnNameVisitor columnNameVisitor = new ColumnNameVisitor();
        int cellnum = 0;
        for (Column column : table.getColumns()) {
            String colName = column.accept(columnNameVisitor);

            Cell cell = row.createCell(cellnum++);
            cell.setCellValue(colName);
        }
    }

    private void storeRow(Row data, List<Column> columns, org.apache.poi.ss.usermodel.Row excelRow) {

        Object[] values = data.getValues();
        for (int i = 0; i < values.length; i++) {
            final Cell cell = excelRow.createCell(i);
            final Object objVal = values[i];
            final Column column = columns.get(i);
            column.accept(new Column.Visitor<Void>() {

                @Override
                public Void visit(StringColumn column) {
                    cell.setCellValue(objVal.toString());
                    return null;
                }

                @Override
                public Void visit(BigIntegerColumn column) {
                    cell.setCellValue(((BigInteger) objVal).doubleValue());
                    return null;
                }

                @Override
                public Void visit(BigDecimalColumn column) {
                    cell.setCellValue(((BigDecimal) objVal).doubleValue());
                    return null;
                }

                @Override
                public Void visit(DateColumn column) {
                    cell.setCellValue((Date) objVal);
                    return null;
                }

                @Override
                public Void visit(TimestampColumn column) {
                    cell.setCellValue((Date) objVal);
                    return null;
                }

                @Override
                public Void visit(CharColumn column) {
                    cell.setCellValue(objVal.toString());
                    return null;
                }

                @Override
                public Void visit(SequenceColumn column) {
                    cell.setCellValue(objVal.toString());
                    return null;
                }

                @Override
                public Void visit(PhoneNumberColumn column) {
                    cell.setCellValue(objVal.toString());
                    return null;
                }
            });


        }

    }

    private OutputStream getOutputStream(String tableName) {
        Path filePath = getOutputFile(tableName);
        try {
            return fileSystem.create(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error creating  file " + filePath, e);
        }
    }

    private Path getOutputFile(String tableName) {
        String outputPath = env.getProperty(Properties.OUTPUT_PATH_KEY, Properties.DEFAULT_OUTPUT_PATH_VALUE);

        String sb = outputPath + "/" + tableName + ".xlsx";

        return new Path(sb);
    }

}
