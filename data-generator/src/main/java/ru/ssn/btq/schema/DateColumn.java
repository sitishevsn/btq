package ru.ssn.btq.schema;

public class DateColumn extends GenericColumn {

    public DateColumn(String name) {
        super(name);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "DateColumn{}";
    }
}
