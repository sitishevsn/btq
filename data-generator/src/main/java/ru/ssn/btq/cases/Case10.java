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

public class Case10 {

    public static void main(String[] args) {
        Case10 case10 = new Case10();
        case10.run();
    }

    private void run() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ExcelAppConfig.class);

        Generator generator = ctx.getBean(Generator.class);
        Store store = ctx.getBean("simpleExcelStorage", Store.class);
        Table bigTable = getTable();

        Iterator<Row> data = generator.generateFor(bigTable, 1000);
        store.store(bigTable, data);

    }

    public Table getTable() {
        List<Column> columns = new ArrayList<>();

        columns.add(new SequenceColumn("id"));
        columns.add(new CharColumn("code", 4));
        columns.add(new CharColumn("language", 2));
        columns.add(new StringColumn("A", 10));
        columns.add(new StringColumn("B", 10));
        columns.add(new StringColumn("C", 10));
        columns.add(new StringColumn("D", 10));


        return new Table("case10/table", columns);
    }
}
