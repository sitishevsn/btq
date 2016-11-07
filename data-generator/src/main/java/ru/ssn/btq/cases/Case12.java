package ru.ssn.btq.cases;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.ssn.btq.config.ExcelAppConfig;
import ru.ssn.btq.data.Generator;
import ru.ssn.btq.schema.CharColumn;
import ru.ssn.btq.schema.Column;
import ru.ssn.btq.schema.Row;
import ru.ssn.btq.schema.SequenceColumn;
import ru.ssn.btq.schema.StringColumn;
import ru.ssn.btq.schema.Table;
import ru.ssn.btq.store.Store;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Case12 {

    public static void main(String[] args) {
        Case12 case12 = new Case12();
        case12.run();
    }

    private void run() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ExcelAppConfig.class);
        Generator generator = ctx.getBean(Generator.class);
        Store store = ctx.getBean("simpleExcelStorage", Store.class);

        for (int i = 0; i < 200; i++) {
            long initSeq = 1000L * i;
            Table table = getTable(initSeq, "case12/case12_table_" + i);

            Iterator<Row> data = generator.generateFor(table, 1000);
            store.store(table, data);
        }
    }


    public Table getTable(long initialSequence, String tableName) {
        List<Column> columns = new ArrayList<>();

        columns.add(new SequenceColumn(initialSequence, "id"));
        columns.add(new CharColumn("code", 4));
        columns.add(new CharColumn("language", 2));
        columns.add(new StringColumn("A", 10));
        columns.add(new StringColumn("B", 10));

        return new Table(tableName, columns);
    }

}
