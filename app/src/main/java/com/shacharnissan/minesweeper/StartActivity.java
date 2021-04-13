package com.shacharnissan.minesweeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shacharnissan.minesweeper.logic.DifficultyEnum;

public class StartActivity extends AppCompatActivity {
    private Button btn_start;
    private RadioGroup radiogroup;
    private TextView easy_score_text;
    private TextView med_score_text;
    private TextView hard_score_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        easy_score_text = findViewById(R.id.easy_score_text);
        med_score_text = findViewById(R.id.medium_score_text);
        hard_score_text = findViewById(R.id.hard_score_text);
        btn_start = findViewById(R.id.start_btn);
        radiogroup = findViewById(R.id.radioGroup);

        loadHighScore();
        btn_start.setOnClickListener(v -> btnclicked());

    }

    private void loadHighScore() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int defaultValue = 0;
        int easyScore = sharedPref.getInt(getString(R.string.easy_score_tag), defaultValue);
        int medScore = sharedPref.getInt(getString(R.string.med_score_tag), defaultValue);
        int hardScore = sharedPref.getInt(getString(R.string.hard_score_tag), defaultValue);
        easy_score_text.setText(""+easyScore);
        med_score_text.setText(""+medScore);
        hard_score_text.setText(""+hardScore);
    }

    private void btnclicked() {
        Intent myIntent = new Intent(StartActivity.this, MainActivity.class);
        DifficultyEnum diff = DifficultyEnum.EASY;
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
        myIntent.putExtra(String.format("%d", R.string.diff_tag), String.format("%s", diff)); //Optional parameters
        StartActivity.this.startActivity(myIntent);

    }
}