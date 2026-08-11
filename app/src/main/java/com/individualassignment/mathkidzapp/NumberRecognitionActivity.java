package com.individualassignment.mathkidzapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NumberRecognitionActivity extends BaseExerciseActivity {
    private final Random random = new Random();

    // Provides the title for the number recognition topic screen.
    @Override
    protected String getTopicTitle() {
        return "Number Words";
    }

    // Provides the instruction text for matching numbers to words.
    @Override
    protected String getTopicSubtitle() {
        return "Match a number to its English word.";
    }

    // Creates a randomized number-to-word recognition exercise.
    @Override
    protected Exercise createExercise() {
        // Whole numbers are capped at two digits for the target age group.
        int number = random.nextInt(100);
        String answer = NumberWords.toWords(number);

        List<String> options = new ArrayList<>();
        options.add(answer);
        while (options.size() < 4) {
            String candidate = NumberWords.toWords(random.nextInt(100));
            if (!options.contains(candidate)) {
                options.add(candidate);
            }
        }
        Collections.shuffle(options);

        return new Exercise(
                "Which word says this number?",
                "" + number,
                options,
                answer,
                number + " is read as " + answer + "."
        );
    }
}
