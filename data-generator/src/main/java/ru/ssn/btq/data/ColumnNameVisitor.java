package ru.ssn.btq.data;

import ru.ssn.btq.schema.BigDecimalColumn;
import ru.ssn.btq.schema.BigIntegerColumn;
import ru.ssn.btq.schema.CharColumn;
import ru.ssn.btq.schema.Column;
import ru.ssn.btq.schema.DateColumn;
import ru.ssn.btq.schema.PhoneNumberColumn;
import ru.ssn.btq.schema.SequenceColumn;
import ru.ssn.btq.schema.StringColumn;
import ru.ssn.btq.schema.TimestampColumn;

public class ColumnNameVisitor implements Column.Visitor<String> {
    @Override
    public String visit(StringColumn column) {
        return column.getName();
    }

    @Override
    public String visit(BigIntegerColumn column) {
        return column.getName();
    }

    @Override
    public String visit(BigDecimalColumn column) {
        return column.getName();
    }

    @Override
    public String visit(DateColumn column) {
        return column.getName();
    }

    @Override
    public String visit(TimestampColumn column) {
        return column.getName();
    }

    @Override
    public String visit(CharColumn column) {
        return column.getName();
    }

    @Override
    public String visit(SequenceColumn column) {
        return column.getName();
    }

    @Override
    public String visit(PhoneNumberColumn column) {
        return column.getName();
    }
}
