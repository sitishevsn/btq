package ru.ssn.btq.cases;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.ssn.btq.config.AppConfig;
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

public class Case09 {

    public static void main(String[] args) {
        Case09 case09 = new Case09();
        case09.run();
    }

    private void run() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        Generator generator = ctx.getBean(Generator.class);
        Store store = ctx.getBean(Store.class);
        Table bigTable = getTable();

        Iterator<Row> data = generator.generateFor(bigTable, 1000);
        store.store(bigTable, data);

    }

    public Table getTable() {
        List<Column> columns = new ArrayList<>();

        columns.add(new SequenceColumn("id"));
        columns.add(new CharColumn("code", 4));
        columns.add(new StringColumn("text", 10, "AAAAAAAAAAAABBBBBBBBBBBBCCCCCCCCCCCCCDDDDDDDDDDDDDEEEEEEEEEEEEEFFFFFFFFFFFFFGGGGGGGGGGGGGGG;".toCharArray()));

        return new Table("case09", columns);
    }

}
