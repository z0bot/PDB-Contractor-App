package com.palodurobuilders.contractorapp.utilities;

import java.security.SecureRandom;

public class PropertyCodeGenerator
{
    static final String DIGIT_DICTIONARY = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
    static final SecureRandom random = new SecureRandom();


    public static String generatePropertyCode()
    {
        StringBuilder stringBuilder = new StringBuilder(6);
        for(int i = 0; i < 6; i++)
        {
            stringBuilder.append(DIGIT_DICTIONARY.charAt(random.nextInt(DIGIT_DICTIONARY.length())));
        }
        return stringBuilder.toString();
    }
}
