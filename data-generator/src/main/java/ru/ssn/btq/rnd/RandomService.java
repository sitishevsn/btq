package ru.ssn.btq.rnd;

public interface RandomService {

    int nextInt(int n);

    long nextLong();

    // lifted from http://stackoverflow.com/questions/2546078/java-random-long-number-in-0-x-n-range
    long nextLong(long n);

    double nextDouble();

    Boolean nextBoolean();

    byte[] nextBits(int numBits);

    String numerify(String numberString);

}
