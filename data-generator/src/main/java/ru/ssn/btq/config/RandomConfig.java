package ru.ssn.btq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ssn.btq.rnd.BigNumber;
import ru.ssn.btq.rnd.DateAndTime;
import ru.ssn.btq.rnd.FakeValues;
import ru.ssn.btq.rnd.FakeValuesImpl;
import ru.ssn.btq.rnd.PhoneNumber;
import ru.ssn.btq.rnd.RandomService;
import ru.ssn.btq.rnd.RandomServiceImpl;
import ru.ssn.btq.rnd.Strings;

@Configuration
public class RandomConfig {

    @Bean
    public RandomService randomService() {
        return new RandomServiceImpl();
    }

    @Bean
    public BigNumber bigNumber() {
        return new BigNumber();
    }

    @Bean
    public DateAndTime dateAndTime() {
        return new DateAndTime();
    }

    @Bean
    public PhoneNumber phoneNumber() {
        return new PhoneNumber();
    }

    @Bean
    public Strings strings() {
        return new Strings();
    }

    @Bean
    public FakeValues fakeValues() {
        return new FakeValuesImpl();
    }
}
