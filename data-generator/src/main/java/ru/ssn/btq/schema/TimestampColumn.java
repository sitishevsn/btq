package ru.ssn.btq.schema;

public class TimestampColumn extends GenericColumn {

    private Integer length;

    public TimestampColumn(String name, Integer length) {
        super(name);
        this.length = length;
    }

    public Integer getLength() {
        return length;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "TimestampColumn{" +
                "length=" + length +
                '}';
    }
}
