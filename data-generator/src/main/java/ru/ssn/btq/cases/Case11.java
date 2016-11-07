package ru.ssn.btq.cases;


import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import ru.ssn.btq.Properties;
import ru.ssn.btq.config.ExcelAppConfig;
import ru.ssn.btq.data.Generator;
import ru.ssn.btq.schema.*;
import ru.ssn.btq.store.Store;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Case11 {

    public static void main(String[] args) {
        Case11 case11 = new Case11();
        case11.run();
    }

    private void run() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ExcelAppConfig.class);

        Generator generator = ctx.getBean(Generator.class);
        Store store = ctx.getBean("zipExcelStorage", Store.class);
        Environment env = ctx.getBean(Environment.class);
        FileSystem fs = ctx.getBean(FileSystem.class);


        for (int i = 0; i < 5; i++) {
            long initSeq = 1000L * i;
            Table table = getTable(initSeq != 0L ? initSeq - 100L : initSeq, "case11_table_" + i);

            Iterator<Row> data = generator.generateFor(table, 1000);
            store.store(table, data);
        }

        try {
            ZipOutputStream outputStream = getOutputStream(fs, env, "case11");

            for (int i = 0; i < 5; i++) {
                Path input = getOutputFile(env, "case11_table_" + i);
                FSDataInputStream inputStream = fs.open(input);

                // name the file inside the zip  file
                outputStream.putNextEntry(new ZipEntry("case11_table_" + i + ".zip"));

                IOUtils.copyBytes(inputStream, outputStream, 1024 * 4);

                inputStream.close();
                fs.delete(input, false);
            }

            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("cannot collect results into one zip file", e);
        }
    }


    private Path getOutputFile(Environment env, String tableName) {
        String outputPath = env.getProperty(Properties.OUTPUT_PATH_KEY, Properties.DEFAULT_OUTPUT_PATH_VALUE);

        String sb = outputPath + "/" + tableName + ".zip";

        return new Path(sb);
    }

    private ZipOutputStream getOutputStream(FileSystem fs, Environment env, String tableName) {
        Path filePath = getOutputFile(env, tableName);
        try {
            FSDataOutputStream outputStream = fs.create(filePath);
            // out put file
            ZipOutputStream out = new ZipOutputStream(outputStream);
            return out;
        } catch (IOException e) {
            throw new RuntimeException("Error creating  file " + filePath, e);
        }
    }


    public Table getTable(long initialSequence, String tableName) {
        List<Column> columns = new ArrayList<>();

        columns.add(new SequenceColumn(initialSequence, "id"));
        columns.add(new CharColumn("code", 4));
        columns.add(new CharColumn("language", 2));
        columns.add(new StringColumn("A", 10));
        columns.add(new StringColumn("B", 10));
        columns.add(new StringColumn("C", 10));

        return new Table(tableName, columns);
    }

}
