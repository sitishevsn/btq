package ru.ssn.btq.schema;

public interface Column {

    public <T> T accept(Visitor<T> visitor);

    public static interface Visitor<T> {

        T visit(StringColumn column);

        T visit(BigIntegerColumn column);

        T visit(BigDecimalColumn column);

        T visit(DateColumn column);

        T visit(TimestampColumn column);

        T visit(CharColumn column);

        T visit(SequenceColumn column);

        T visit(PhoneNumberColumn column);

    }

}
