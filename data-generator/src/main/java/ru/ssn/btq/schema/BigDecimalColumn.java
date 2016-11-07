package ru.ssn.btq.schema;

import java.util.Objects;

public class BigDecimalColumn extends GenericColumn {

    private Integer precision;

    private Integer scale;

    public BigDecimalColumn(String name, Integer precision, Integer scale) {
        super(name);
        Objects.requireNonNull(precision);
        Objects.requireNonNull(scale);
        this.precision = precision;
        this.scale = scale;
    }

    public Integer getPrecision() {
        return precision;
    }

    public Integer getScale() {
        return scale;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "BigDecimalColumn{" +
                "precision=" + precision +
                ", scale=" + scale +
                '}';
    }
}
