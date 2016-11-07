package ru.ssn.btq.schema;

public class StringColumn extends GenericColumn {

    private Integer length;

    private char[] alphabet;

    public StringColumn(String name, Integer length) {
        super(name);
        this.length = length;
    }

    public StringColumn(String name, Integer length, char[] alphabet) {
        super(name);
        this.length = length;
        this.alphabet = alphabet;
    }

    public Integer getLength() {
        return length;
    }

    public char[] getAlphabet() {
        return alphabet;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "StringColumn{" +
                "length=" + length +
                '}';
    }
}
