package com.shacharnissan.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shacharnissan.minesweeper.logic.DifficultyEnum;
import com.shacharnissan.minesweeper.logic.StatusEnum;

import java.util.concurrent.TimeUnit;

public class EndActivity extends AppCompatActivity {

    private TextView resultStatus;
    private TextView resultTime;
    private Button btn_start_new_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_activity);

        resultStatus = findViewById(R.id.textView_ResultStatus);
        resultTime = findViewById(R.id.textView_ResultTime);
        btn_start_new_game = (Button) findViewById(R.id.start_btn);
        btn_start_new_game.setOnClickListener(v -> btnclicked());

        showGameResult();
    }

    private void showGameResult() {
        String status, time;

        Intent intent = getIntent();
        status = intent.getStringExtra(String.format("%d", R.string.status_tag));
        time = intent.getStringExtra(String.format("%d", R.string.time_tag));
        this.resultStatus.setText(checkStatus(status));
        this.resultTime.setText(writeTimeClockFormat(Integer.parseInt(time)));
    }

    private String writeTimeClockFormat(int time) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(time), TimeUnit.MILLISECONDS.toSeconds(time) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
    }

    private int checkStatus(String status) {
        if (status.equals("" + StatusEnum.WIN))
            return R.string.game_win;
        else
            return R.string.game_lose;
    }

    private void btnclicked() {
        String difficultyLevel = getDifficultyLevel();
        sendDifficultyLevelToNextActivity(DifficultyEnum.valueOf(difficultyLevel));
    }

    private void sendDifficultyLevelToNextActivity(DifficultyEnum diff) {
        String key, value;
        key = String.format("%d", R.string.diff_tag);
        value = String.format("%s", diff);
        Intent myIntent = new Intent(EndActivity.this, MainActivity.class);
        myIntent.putExtra(key,value); //Optional parameters
        EndActivity.this.startActivity(myIntent);
    }

    private String getDifficultyLevel() {
        Intent intent = getIntent();
        String key = String.format("%d", R.string.diff_tag);
        String value = intent.getStringExtra(key); //if it's a string you stored.
        return value;
    }

}