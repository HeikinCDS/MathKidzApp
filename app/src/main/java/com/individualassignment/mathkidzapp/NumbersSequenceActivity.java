package com.individualassignment.mathkidzapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NumbersSequenceActivity extends BaseExerciseActivity {
    private final Random random = new Random();

    // Provides the title for the number sequence topic screen.
    @Override
    protected String getTopicTitle() {
        return "Number Sequence";
    }

    // Provides the instruction text for arranging numbers in order.
    @Override
    protected String getTopicSubtitle() {
        return "Put numbers in order from small to big or from big to small.";
    }

    // Creates a randomized sequence exercise with ascending or descending order.
    @Override
    protected Exercise createExercise() {
        // The children choose a complete ordered row
        boolean ascending = random.nextBoolean();
        List<Integer> numbers = makeUniqueNumbers();
        List<Integer> shownNumbers = new ArrayList<>(numbers);
        Collections.shuffle(shownNumbers);

        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        Collections.sort(sortedNumbers);
        if (!ascending) {
            Collections.reverse(sortedNumbers);
        }

        String answer = joinNumbers(sortedNumbers);
        List<String> options = new ArrayList<>();
        options.add(answer);
        while (options.size() < 4) {
            List<Integer> candidate = new ArrayList<>(numbers);
            Collections.shuffle(candidate);
            String option = joinNumbers(candidate);
            if (!options.contains(option)) {
                options.add(option);
            }
        }
        Collections.shuffle(options);

        String direction = ascending ? "smallest to biggest" : "biggest to smallest";
        return new Exercise(
                "Choose the row in this order:<br><font color='#4361EE'><b>" + direction + "</b></font>",
                "Numbers: " + joinNumbers(shownNumbers),
                options,
                answer,
                "The correct order is " + answer + "."
        );
    }

    // Generates four unique two-digit-or-less numbers for a sequence question.
    private List<Integer> makeUniqueNumbers() {
        List<Integer> numbers = new ArrayList<>();
        while (numbers.size() < 4) {
            int candidate = random.nextInt(100);
            if (!numbers.contains(candidate)) {
                numbers.add(candidate);
            }
        }
        return numbers;
    }

    // Converts a list of numbers into a comma-separated answer string.
    private String joinNumbers(List<Integer> numbersList) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numbersList.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(numbersList.get(i));
        }
        return builder.toString();
    }
}
