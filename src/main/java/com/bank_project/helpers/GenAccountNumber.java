package com.bank_project.helpers;

import java.util.Random;

public class GenAccountNumber {

    public static String generateAccountNumber() {
        Random random = new Random();
        int bound = 1000;
        int account_number = bound * random.nextInt(bound) + random.nextInt(bound);
        return Integer.toString(account_number);
    }
}
