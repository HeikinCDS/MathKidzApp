package com.individualassignment.mathkidzapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Sets up the home screen and connects each topic button to its activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnCounting).setOnClickListener(view ->
                openTopic(CountObjectActivity.class));
        findViewById(R.id.btnPlaceValue).setOnClickListener(view ->
                openTopic(PlaceValueActivity.class));
        findViewById(R.id.btnNumberWords).setOnClickListener(view ->
                openTopic(NumberRecognitionActivity.class));
        findViewById(R.id.btnSequence).setOnClickListener(view ->
                openTopic(NumbersSequenceActivity.class));
    }

    // Opens the selected topic activity from the main menu.
    private void openTopic(Class<?> activityClass) {
        startActivity(new Intent(this, activityClass));
    }
}
