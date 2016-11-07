package ru.ssn.btq.rnd;

import org.springframework.beans.factory.annotation.Autowired;

public class PhoneNumber {

    private static final String[] PHONE_NUMBER_FORMATS = new String[]{"(9##)###-##-##", "+7##########", "##########", "#######"};

    @Autowired
    private RandomService rnd;

    public String nextPhoneNumber() {
        int index = rnd.nextInt(PHONE_NUMBER_FORMATS.length);
        String phoneNumberFormat = PHONE_NUMBER_FORMATS[index];

        return rnd.numerify(phoneNumberFormat);
    }

}
