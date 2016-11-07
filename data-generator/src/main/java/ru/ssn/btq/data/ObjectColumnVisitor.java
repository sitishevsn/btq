package ru.ssn.btq.data;

import ru.ssn.btq.rnd.FakeValues;
import ru.ssn.btq.schema.BigDecimalColumn;
import ru.ssn.btq.schema.BigIntegerColumn;
import ru.ssn.btq.schema.CharColumn;
import ru.ssn.btq.schema.Column;
import ru.ssn.btq.schema.DateColumn;
import ru.ssn.btq.schema.PhoneNumberColumn;
import ru.ssn.btq.schema.SequenceColumn;
import ru.ssn.btq.schema.StringColumn;
import ru.ssn.btq.schema.TimestampColumn;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ObjectColumnVisitor implements Column.Visitor<Object> {

    private final Date now = new Date();
    private final Date from = new Date(now.getTime() - 1000 * 60 * 60 * 24 * 10);
    private final Date to = new Date(now.getTime() + 1000 * 60 * 60 * 24 * 10);

    private final FakeValues fakeValues;

    private final Map<String, AtomicLong> sequences = new HashMap<>();

    public ObjectColumnVisitor(FakeValues fakeValues) {
        this.fakeValues = fakeValues;
    }

    @Override
    public Object visit(StringColumn column) {
        char[] alphabet = column.getAlphabet();
        if (alphabet == null) {
            return fakeValues.strings().nextString(column.getLength());
        } else {
            return fakeValues.strings().nextString(column.getLength(), alphabet);
        }
    }

    @Override
    public Object visit(BigIntegerColumn column) {
        Integer length = column.getLength();
        if (length == null) {
            length = 10;
        }
        return fakeValues.bigNumber().nextBigInteger(length);
    }

    @Override
    public Object visit(BigDecimalColumn column) {
        return fakeValues.bigNumber().nextBigDecimal(column.getPrecision(), column.getScale());
    }

    @Override
    public Object visit(DateColumn column) {
        return fakeValues.dateAndTime().between(from, to);
    }

    @Override
    public Object visit(TimestampColumn column) {
        return fakeValues.dateAndTime().nextTimestamp(from, to, column.getLength());
    }

    @Override
    public Object visit(CharColumn column) {
        char[] alphabet = column.getAlphabet();
        if (alphabet == null) {
            return fakeValues.strings().nextChar(column.getLength());
        } else {
            return fakeValues.strings().nextChar(column.getLength(), alphabet);
        }
    }

    @Override
    public Object visit(SequenceColumn column) {
        AtomicLong counter = sequences.get(column.getName());
        if (counter == null) {
            counter = new AtomicLong(column.getInitialSequence());
            sequences.put(column.getName(), counter);
        }
        return counter.incrementAndGet();
    }

    @Override
    public Object visit(PhoneNumberColumn column) {
        return fakeValues.phoneNumber().nextPhoneNumber();
    }
}
