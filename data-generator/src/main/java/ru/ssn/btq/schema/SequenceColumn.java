package ru.ssn.btq.schema;

public class SequenceColumn extends GenericColumn {

    private long initialSequence;

    public SequenceColumn(String name) {
        super(name);
        this.initialSequence = 0;
    }

    public SequenceColumn(long initialSequence, String name) {
        super(name);
        this.initialSequence = initialSequence;
    }

    public long getInitialSequence() {
        return initialSequence;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
