package com.shacharnissan.minesweeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shacharnissan.minesweeper.logic.DifficultyEnum;

import java.util.concurrent.TimeUnit;


public class StartActivity extends AppCompatActivity {
    private Button btn_start;
    private Button btn_highest_score;

    private RadioGroup radiogroup;
    private TextView easy_score_text;
    private TextView med_score_text;
    private TextView hard_score_text;

    private HighScoresFragment highScoresFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        easy_score_text = findViewById(R.id.easy_score_text);
        med_score_text = findViewById(R.id.medium_score_text);
        hard_score_text = findViewById(R.id.hard_score_text);
        btn_start = findViewById(R.id.start_btn);
        radiogroup = findViewById(R.id.radioGroup);
        btn_start.setOnClickListener(v -> startBtnClicked());
        btn_highest_score = findViewById(R.id.highest_score_btn);
        btn_highest_score.setOnClickListener(v -> openHighestScoreFragment());

        this.highScoresFragment = new HighScoresFragment();
    }

    private void openHighestScoreFragment() {
        Log.d("fragment btn", "open fragment highest scores");
        getSupportFragmentManager().beginTransaction().replace(R.id.start_activity,highScoresFragment).commit();
        btn_start.setVisibility(View.INVISIBLE);
        btn_highest_score.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (highScoresFragment == null || highScoresFragment.isRemoving())
            super.onBackPressed();
        else{
            getSupportFragmentManager().beginTransaction().remove(highScoresFragment).commit();
            btn_start.setVisibility(View.VISIBLE);
            btn_highest_score.setVisibility(View.VISIBLE);
        }
    }

    private void startBtnClicked() {
        DifficultyEnum diff = getDifficultyLevelFromUser();
        sendDifficultyLevelToNextActivity(diff);
    }

    private void sendDifficultyLevelToNextActivity(DifficultyEnum diff) {
        String key, value;
        key = String.format("%d", R.string.diff_tag);
        value = String.format("%s", diff);
        Intent myIntent = new Intent(StartActivity.this, MainActivity.class);
        myIntent.putExtra(key,value); //Optional parameters
        StartActivity.this.startActivity(myIntent);
    }

    private DifficultyEnum getDifficultyLevelFromUser() {
        DifficultyEnum diff = DifficultyEnum.EASY;   // the default value
        switch (radiogroup.getCheckedRadioButtonId()) {
            case R.id.radioButton:
                diff = DifficultyEnum.EASY;
                break;
            case R.id.radioButton2:
                diff = DifficultyEnum.MEDIUM;
                break;
            case R.id.radioButton3:
                diff = DifficultyEnum.HARD;
                break;
        }
        return diff;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume", "onResume");
        getHighestResults();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onPause", "onPause");
    }

    private void getHighestResults() {
        String filename = getResources().getString(R.string.score_filename);
        SharedPreferences sharedPref = getSharedPreferences(filename,Context.MODE_PRIVATE);

        // for reset all highest results:
        // sharedPref.edit().clear().commit();

        long easyHighestResult = sharedPref.getLong(getString(R.string.easy_score_tag), 0);
        long medHighestResult = sharedPref.getLong(getString(R.string.med_score_tag), 0);
        long hardHighestResult = sharedPref.getLong(getString(R.string.hard_score_tag), 0);

        // put values timers:
        easy_score_text.setText(writeTimeClockFormat(easyHighestResult));
        med_score_text.setText(writeTimeClockFormat(medHighestResult));
        hard_score_text.setText(writeTimeClockFormat(hardHighestResult));
    }

    private String writeTimeClockFormat(long time) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(time), TimeUnit.MILLISECONDS.toSeconds(time) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
    }
}