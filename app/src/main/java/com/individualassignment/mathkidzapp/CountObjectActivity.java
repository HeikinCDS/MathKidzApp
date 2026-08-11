package com.individualassignment.mathkidzapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CountObjectActivity extends BaseExerciseActivity {
    private final Random random = new Random();
    private final String[] objects = {"🍎", "⭐️", "🐼", "🚀", "🍦", "🎈"};

    // Provides the title for the object counting topic screen.
    @Override
    protected String getTopicTitle() {
        return "Count Objects";
    }

    // Provides the instruction text for the object counting topic.
    @Override
    protected String getTopicSubtitle() {
        return "Look at the objects, count them, and then choose the matching number.";
    }

    // Creates a randomized object counting exercise with four answer choices.
    @Override
    protected Exercise createExercise() {
        // Single-digit object counting questions
        int count = random.nextInt(9) + 1;
        String object = objects[random.nextInt(objects.length)];
        StringBuilder visual = new StringBuilder();
        for (int i = 0; i < count; i++) {
            visual.append(object).append(" ");
            if ((i + 1) % 5 == 0) {
                visual.append("\n");
            }
        }

        List<String> options = new ArrayList<>();
        options.add("" + count);
        while (options.size() < 4) {
            int candidate = random.nextInt(9) + 1;
            String option = "" + candidate;
            if (!options.contains(option)) {
                options.add(option);
            }
        }
        Collections.shuffle(options);

        return new Exercise(
                "How many objects can you see?",
                visual.toString().trim(),
                options,
                "" + count,
                "There are " + NumberWords.toWords(count) + " objects."
        );
    }
}
