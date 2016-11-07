package ru.ssn.btq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import ru.ssn.btq.data.Generator;
import ru.ssn.btq.data.GeneratorImpl;
import ru.ssn.btq.schema.ColumnFactory;
import ru.ssn.btq.schema.ColumnFactoryImpl;
import ru.ssn.btq.schema.TableReader;
import ru.ssn.btq.schema.TableReaderImpl;
import ru.ssn.btq.store.Store;
import ru.ssn.btq.store.StoreImpl;


@Import({HadoopConfig.class, RandomConfig.class})
public class AppConfig {

    @Bean
    public ColumnFactory columnFactory() {
        return new ColumnFactoryImpl();
    }

    @Bean
    public TableReader tableReader() {
        return new TableReaderImpl();
    }

    @Bean
    public Generator generator() {
        return new GeneratorImpl();
    }

    @Bean Store store() {
        return new StoreImpl();
    }

}
