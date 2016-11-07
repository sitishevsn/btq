package ru.ssn.btq.rnd;

import org.springframework.beans.factory.annotation.Autowired;

public class Strings {

    private final char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    @Autowired
    private RandomService rnd;

    public String nextString(int length) {
        return nextString(length, chars);
    }

    public String nextString(int length, char[] alphabet) {
        int rndLength = rnd.nextInt(length + 1) + 1;
        return generateString(rndLength, alphabet);
    }

    public String nextChar(Integer length) {
        return nextChar(length, chars);
    }

    public String nextChar(Integer length, char[] alphabet) {
        return generateString(length, alphabet);
    }

    private String generateString(int length, char[] alphabet) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = alphabet[rnd.nextInt(alphabet.length)];
            sb.append(c);
        }
        return sb.toString();
    }


}
