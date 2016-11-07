package ru.ssn.btq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import ru.ssn.btq.data.Generator;
import ru.ssn.btq.data.GeneratorImpl;
import ru.ssn.btq.store.ExcelStoreImpl;
import ru.ssn.btq.store.Store;
import ru.ssn.btq.store.ZipExcelStoreImpl;

@Import({HadoopConfig.class, RandomConfig.class})
public class ExcelAppConfig {

    @Bean
    public Generator generator() {
        return new GeneratorImpl();
    }

    @Bean(name = "simpleExcelStorage")
    Store storeSimple() {
        return new ExcelStoreImpl();
    }

    @Bean(name = "zipExcelStorage")
    Store storeZip() {
        return new ZipExcelStoreImpl();
    }
}

