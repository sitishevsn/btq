package ru.ssn.btq.schema;

import java.util.Objects;

public class BigIntegerColumn extends GenericColumn {

    private Integer length;

    public BigIntegerColumn(String name, Integer length) {
        super(name);
        Objects.requireNonNull(length);
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
        return "BigIntegerColumn{" +
                "length=" + length +
                '}';
    }
}
