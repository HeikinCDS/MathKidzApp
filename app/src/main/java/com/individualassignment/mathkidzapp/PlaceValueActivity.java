package com.individualassignment.mathkidzapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PlaceValueActivity extends BaseExerciseActivity {
    private final Random random = new Random();

    // Provides the title for the place value topic screen.
    @Override
    protected String getTopicTitle() {
        return "Place Value";
    }

    // Provides the instruction text for the place value topic.
    @Override
    protected String getTopicSubtitle() {
        return "Learn how a digit changes value when it sits in the tens or ones place.";
    }

    // Creates a randomized two-digit place value exercise.
    @Override
    protected Exercise createExercise() {
        // Two-digit numbers let children practise tens and ones place value.
        int number = random.nextInt(90) + 10;
        int tensDigit = number / 10;
        int onesDigit = number % 10;
        boolean askTens = random.nextBoolean();

        String prompt;
        String answer;
        String explanation;
        if (askTens) {
            prompt = "In the number " + number + ", which digit is in the tens place?";
            answer = "" + tensDigit;
            explanation = "The digit " + tensDigit + " is in the tens place. "
                    + number + " has " + tensDigit + " as tens and " + onesDigit + " as ones.";
        } else {
            prompt = "In the number " + number + ", which digit is in the ones place?";
            answer = "" + onesDigit;
            explanation = "The digit " + onesDigit + " is in the ones place. "
                    + number + " has " + tensDigit + " as tens and " + onesDigit + " as ones.";
        }

        List<String> options = new ArrayList<>();
        options.add(answer);
        while (options.size() < 4) {
            String candidate = "" + random.nextInt(10);
            if (!options.contains(candidate)) {
                options.add(candidate);
            }
        }
        Collections.shuffle(options);

        String visual = "" + number;
        return new Exercise(prompt, visual, options, answer, explanation);
    }
}
