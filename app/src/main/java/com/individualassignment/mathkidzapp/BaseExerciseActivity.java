package com.individualassignment.mathkidzapp;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;

public abstract class BaseExerciseActivity extends AppCompatActivity {
    private static final int ROUND_SIZE = 5;

    private TextView scoreView;
    private TextView promptView;
    private TextView visualView;
    private TextView feedbackView;
    private LinearLayout optionContainer;
    private Button nextButton;
    private Exercise currentExercise;
    private int correctCount = 0;
    private int attemptedCount = 0;
    private boolean answered = false;
    private boolean showingResults = false;

    // Returns the title shown at the top of each topic screen.
    protected abstract String getTopicTitle();

    // Returns the short instruction text shown below the topic title.
    protected abstract String getTopicSubtitle();

    // Creates a new randomized exercise for the current topic.
    protected abstract Exercise createExercise();

    // Builds the exercise screen and loads the first question when the activity opens.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildScreen();
        showNextExercise();
    }

    // Creates the shared quiz layout used by all topic activities.
    private void buildScreen() {
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(getColor(R.color.background));

        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f);
        mainLayout.addView(scrollView, scrollParams);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(40), dp(20), dp(12));
        scrollView.addView(root);

        Button backButton = makeActionButton(getString(R.string.btn_back));
        backButton.setOnClickListener(view -> finish());
        root.addView(backButton, fullWidth(dp(52)));

        TextView titleView = makeText(getTopicTitle(), 32, R.color.primary, true);
        titleView.setPadding(0, dp(12), 0, 0);
        root.addView(titleView);

        TextView subtitleView = makeText(getTopicSubtitle(), 18, R.color.text_dark, false);
        subtitleView.setPadding(0, dp(2), 0, dp(8));
        root.addView(subtitleView);

        scoreView = makeText("", 18, R.color.primary, true);
        scoreView.setGravity(Gravity.END);
        root.addView(scoreView);

        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(dp(18), dp(18), dp(18), dp(18));
        panel.setBackgroundResource(R.drawable.panel);
        LinearLayout.LayoutParams panelParams = fullWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        panelParams.setMargins(0, dp(12), 0, dp(10));
        root.addView(panel, panelParams);

        promptView = makeText("", 22, R.color.text_dark, true);
        panel.addView(promptView);

        visualView = makeText("", 36, R.color.pink, true);
        visualView.setGravity(Gravity.CENTER);
        visualView.setPadding(0, dp(16), 0, dp(12));
        panel.addView(visualView);

        optionContainer = new LinearLayout(this);
        optionContainer.setOrientation(LinearLayout.VERTICAL);
        root.addView(optionContainer);

        feedbackView = makeText("", 20, R.color.text_dark, true);
        feedbackView.setGravity(Gravity.CENTER);
        feedbackView.setPadding(dp(16), dp(8), dp(16), dp(8));
        root.addView(feedbackView);

        nextButton = makeActionButton(getString(R.string.btn_next));
        nextButton.setOnClickListener(view -> handleNextButton());
        LinearLayout.LayoutParams nextParams = fullWidth(dp(60));
        nextParams.setMargins(dp(20), dp(0), dp(20), dp(20));
        mainLayout.addView(nextButton, nextParams);

        setContentView(mainLayout);
    }

    // Decides whether the next button should load a question, show results, or restart the round.
    private void handleNextButton() {
        if (showingResults) {
            restartRound();
        } else if (answered && attemptedCount >= ROUND_SIZE) {
            showResults();
        } else {
            showNextExercise();
        }
    }

    // Loads a new exercise and displays its question, visual content, and answer options.
    private void showNextExercise() {
        currentExercise = createExercise();
        answered = false;
        showingResults = false;
        updateScore();
        promptView.setText(Html.fromHtml(currentExercise.prompt, Html.FROM_HTML_MODE_LEGACY));
        visualView.setText(currentExercise.visual);
        feedbackView.setText("");
        nextButton.setText(getString(R.string.btn_next));
        nextButton.setVisibility(View.GONE);

        optionContainer.removeAllViews();
        for (String option : currentExercise.options) {
            Button button = makeOptionButton(option);
            button.setOnClickListener(view -> checkAnswer((Button) view));
            LinearLayout.LayoutParams params = fullWidth(dp(54));
            params.setMargins(0, dp(6), 0, 0);
            optionContainer.addView(button, params);
        }
    }

    // Checks the selected answer and updates feedback, score, and button state.
    private void checkAnswer(Button selectedButton) {
        if (answered) return;
        answered = true;
        attemptedCount++;
        boolean isCorrect = Objects.equals(selectedButton.getText().toString(), currentExercise.answer);
        if (isCorrect) {
            correctCount++;
            selectedButton.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.success_green)));
            selectedButton.setTextColor(Color.WHITE);
            feedbackView.setText(getString(R.string.feedback_correct, currentExercise.explanation));
            feedbackView.setTextColor(getColor(R.color.success_green));
        } else {
            selectedButton.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.pink)));
            selectedButton.setTextColor(Color.WHITE);
            feedbackView.setText(getString(R.string.feedback_incorrect, currentExercise.explanation));
            feedbackView.setTextColor(getColor(R.color.pink));
        }
        disableOptions();
        updateScore();
        if (attemptedCount >= ROUND_SIZE) {
            nextButton.setText(getString(R.string.btn_results));
        }
        nextButton.setVisibility(View.VISIBLE);
    }

    // Displays the final result after the five-question round is completed.
    private void showResults() {
        showingResults = true;
        answered = false;
        optionContainer.removeAllViews();
        promptView.setText(getString(R.string.results_title));
        visualView.setText(getString(R.string.results_score, correctCount, ROUND_SIZE));
        feedbackView.setText(makeResultMessage());
        feedbackView.setTextColor(getColor(R.color.primary));
        nextButton.setText(getString(R.string.btn_try_again));
        nextButton.setVisibility(View.VISIBLE);
        updateScore();
    }

    // Resets the score and starts a new five-question round.
    private void restartRound() {
        correctCount = 0;
        attemptedCount = 0;
        showingResults = false;
        showNextExercise();
    }

    // Chooses the final message based on the user's score.
    private String makeResultMessage() {
        if (correctCount == ROUND_SIZE) return getString(R.string.results_perfect);
        if (correctCount >= 3) return getString(R.string.results_good);
        return getString(R.string.results_practice);
    }

    // Disables all answer buttons and highlights the correct answer.
    private void disableOptions() {
        for (int i = 0; i < optionContainer.getChildCount(); i++) {
            View child = optionContainer.getChildAt(i);
            child.setEnabled(false);
            if (child instanceof Button) {
                Button button = (Button) child;
                if (Objects.equals(button.getText().toString(), currentExercise.answer)) {
                    button.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.success_green)));
                    button.setTextColor(Color.WHITE);
                }
            }
        }
    }

    // Updates the progress and score text shown on the topic screen.
    private void updateScore() {
        if (showingResults) {
            scoreView.setText(getString(R.string.round_finished));
            return;
        }
        scoreView.setText(getString(R.string.score_format, correctCount, attemptedCount));
    }

    // Converts density-independent pixels to actual screen pixels.
    protected int dp(int value) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(value * density);
    }

    // Creates a TextView with consistent size, color, and bold styling.
    private TextView makeText(String text, int sizeSp, int colorRes, boolean bold) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(sizeSp);
        textView.setTextColor(getColor(colorRes));
        if (bold) textView.setTypeface(textView.getTypeface(), android.graphics.Typeface.BOLD);
        return textView;
    }

    // Creates the main action buttons, such as Back, Next, and Try Again.
    private Button makeActionButton(String text) {
        Button button = new Button(this);
        button.setText(text);
        button.setAllCaps(false);
        button.setTextSize(18);
        button.setTextColor(Color.WHITE);
        button.setBackgroundResource(R.drawable.primary_button);
        button.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.primary)));
        return button;
    }

    // Creates an answer option button for the current exercise.
    private Button makeOptionButton(String text) {
        Button button = new Button(this);
        button.setText(text);
        button.setAllCaps(false);
        button.setTextSize(22);
        button.setTextColor(getColor(R.color.text_dark));
        button.setBackgroundResource(R.drawable.option_button);
        return button;
    }

    // Creates layout parameters that make a view fill the screen width.
    private LinearLayout.LayoutParams fullWidth(int height) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
    }
}
