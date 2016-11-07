package ru.ssn.btq.schema;

public class PhoneNumberColumn extends GenericColumn {

    public PhoneNumberColumn(String name) {
        super(name);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
