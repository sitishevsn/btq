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

public class Case13 {

    public static void main(String[] args) {
        Case13 case13 = new Case13();
        case13.run();
    }

    private void run() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ExcelAppConfig.class);
        Generator generator = ctx.getBean(Generator.class);
        Store store = ctx.getBean("simpleExcelStorage", Store.class);

        Table table = getTable(0, "case13/case13_table_pattern");

        Iterator<Row> data = generator.generateFor(table, 1000);
        store.store(table, data);
    }


    public Table getTable(long initialSequence, String tableName) {
        List<Column> columns = new ArrayList<>();

        columns.add(new SequenceColumn(initialSequence, "id"));
        columns.add(new CharColumn("AA", 4));
        columns.add(new CharColumn("BB", 2));
        columns.add(new CharColumn("CC", 4));
        columns.add(new CharColumn("DD", 2));

        return new Table(tableName, columns);
    }

}
