package ru.ssn.btq.rnd;

import org.springframework.beans.factory.annotation.Autowired;

public class FakeValuesImpl implements FakeValues {

    @Autowired
    private BigNumber bigNumber;

    @Autowired
    private DateAndTime dateAndTime;

    @Autowired
    private PhoneNumber phoneNumber;

    @Autowired
    private Strings strings;

    @Override
    public BigNumber bigNumber() {
        return bigNumber;
    }

    @Override
    public DateAndTime dateAndTime() {
        return dateAndTime;
    }

    @Override
    public PhoneNumber phoneNumber() {
        return phoneNumber;
    }

    @Override
    public Strings strings() {
        return strings;
    }
}
