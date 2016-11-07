package ru.ssn.btq.cases;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.ssn.btq.config.ExcelAppConfig;
import ru.ssn.btq.data.Generator;
import ru.ssn.btq.schema.CharColumn;
import ru.ssn.btq.schema.Column;
import ru.ssn.btq.schema.Row;
import ru.ssn.btq.schema.SequenceColumn;
import ru.ssn.btq.schema.Table;
import ru.ssn.btq.store.Store;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Case14 {

    public static void main(String[] args) {
        Case14 case14 = new Case14();
        case14.run();
    }

    private void run() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ExcelAppConfig.class);
        Generator generator = ctx.getBean(Generator.class);
        Store store = ctx.getBean("simpleExcelStorage", Store.class);

        Table table1 = getTable1(0, "case14/case14_table_1");
        Table table2 = getTable2(0, "case14/case14_table_2");

        Iterator<Row> data1 = generator.generateFor(table1, 1000);
        store.store(table1, data1);

        Iterator<Row> data2 = generator.generateFor(table2, 1000);
        store.store(table2, data2);
    }


    public Table getTable1(long initialSequence, String tableName) {
        List<Column> columns = new ArrayList<>();

        columns.add(new SequenceColumn(initialSequence, "id"));
        columns.add(new CharColumn("AA", 4));
        columns.add(new CharColumn("BB", 2));
        columns.add(new CharColumn("CC", 4));
        columns.add(new CharColumn("DD", 2));

        return new Table(tableName, columns);
    }

    public Table getTable2(long initialSequence, String tableName) {
        List<Column> columns = new ArrayList<>();

        columns.add(new SequenceColumn(initialSequence, "id"));
        columns.add(new CharColumn("EE", 4));
        columns.add(new CharColumn("FF", 2));
        columns.add(new CharColumn("JJ", 4));

        return new Table(tableName, columns);
    }

}
