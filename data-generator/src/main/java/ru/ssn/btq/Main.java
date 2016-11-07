package ru.ssn.btq;

import com.google.common.base.Splitter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import ru.ssn.btq.config.AppConfig;
import ru.ssn.btq.data.Generator;
import ru.ssn.btq.schema.Row;
import ru.ssn.btq.schema.Table;
import ru.ssn.btq.schema.TableReader;
import ru.ssn.btq.store.Store;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        new Main().work();
    }

    private void work() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        TableReader reader = ctx.getBean(TableReader.class);
        Generator generator = ctx.getBean(Generator.class);
        Store store = ctx.getBean(Store.class);
        Environment env = ctx.getBean(Environment.class);

        for (String tableName : getTableNames(env)) {
            String tablePath = "schema/" + tableName + ".csv";

            Table table = reader.read(tablePath);
            Iterator<Row> iterator = generator.generateFor(table);
            store.store(table, iterator);
        }

    }

    public Iterable<String> getTableNames(Environment env) {
        String tables = env.getProperty(Properties.INPUT_TABLES_KEY, Properties.DEFAULT_INPUT_TABLE);
        if ("all".equalsIgnoreCase(tables)) {
            return Splitter.on(",").omitEmptyStrings().trimResults().split(Properties.ALL_INPUT_TABLES);
        } else {
            return Splitter.on(",").omitEmptyStrings().trimResults().split(tables);
        }

    }
}
