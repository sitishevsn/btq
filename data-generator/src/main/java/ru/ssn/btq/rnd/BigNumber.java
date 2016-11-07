package ru.ssn.btq.rnd;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigNumber {

    @Autowired
    private RandomService rnd;

    public BigInteger nextBigInteger(int precision) {
        return newRandomBigInteger(precision);
    }

    public BigDecimal nextBigDecimal(int precision, int scale) {
        return new BigDecimal(nextBigInteger(precision), scale);
    }

    private BigInteger newRandomBigInteger(int precision) {
        BigInteger n = BigInteger.TEN.pow(precision);
        return newRandomBigInteger(n);
    }

    private BigInteger newRandomBigInteger(BigInteger n) {
        BigInteger r;
        do {
            r = new BigInteger(1, rnd.nextBits(n.bitCount()));
        } while (r.compareTo(n) >= 0);

        return r;
    }

}
