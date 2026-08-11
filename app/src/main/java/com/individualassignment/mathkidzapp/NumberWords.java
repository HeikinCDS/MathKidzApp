package com.individualassignment.mathkidzapp;

public class NumberWords {
    private static final String[] SMALL = {
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight",
            "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen",
            "sixteen", "seventeen", "eighteen", "nineteen"
    };

    private static final String[] TENS = {
            "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy",
            "eighty", "ninety"
    };

    // Converts a whole number from 0 to 99 into English words.
    static String toWords(int number) {
        if (number < 20) {
            return SMALL[number];
        }
        int tens = number / 10;
        int ones = number % 10;
        if (ones == 0) {
            return TENS[tens];
        }
        return TENS[tens] + " " + SMALL[ones];
    }
}
