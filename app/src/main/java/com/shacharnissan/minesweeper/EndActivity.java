package com.shacharnissan.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shacharnissan.minesweeper.logic.DifficultyEnum;

public class EndActivity extends AppCompatActivity {

    private TextView gameStatus;
    private TextView resultTime;
    private Button btn_start_new_game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_activity);

//        gameStatus = findViewById(R.id.textView_GameStatus);
//        gameStatus = findViewById(R.id.textView_ResultTime);
        btn_start_new_game = (Button) findViewById(R.id.start_btn);

        btn_start_new_game.setOnClickListener(v -> btnclicked());
    }

    private void btnclicked() {
        Intent myIntent = new Intent(EndActivity.this, MainActivity.class);
        DifficultyEnum diff = DifficultyEnum.EASY;

        myIntent.putExtra(String.format("%d", R.string.diff_tag), String.format("%s", diff)); //Optional parameters
        EndActivity.this.startActivity(myIntent);

    }
}