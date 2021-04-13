package com.shacharnissan.minesweeper;

import android.content.Intent;
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
    private TextView score_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        btn_start = (Button) findViewById(R.id.start_btn);
        radiogroup = findViewById(R.id.radioGroup);

        btn_start.setOnClickListener(v -> btnclicked());


    }

    private void btnclicked() {
        Intent myIntent = new Intent(StartActivity.this, AppCompatActivity.class);
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