package com.individualassignment.mathkidzapp;

import java.util.List;

public class Exercise {
    final String prompt;
    final String visual;
    final List<String> options;
    final String answer;
    final String explanation;

    // Stores all information needed to display and check one exercise.
    Exercise(String prompt, String visual, List<String> options, String answer, String explanation) {
        this.prompt = prompt; //the question
        this.visual = visual; //the objects in the question
        this.options = options; //answer choices
        this.answer = answer; //correct answer
        this.explanation = explanation; //feedback shown after answering
    }
}
