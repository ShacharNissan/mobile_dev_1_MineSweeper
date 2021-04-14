package com.shacharnissan.minesweeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shacharnissan.minesweeper.logic.DifficultyEnum;
import com.shacharnissan.minesweeper.logic.Game;
import com.shacharnissan.minesweeper.logic.StatusEnum;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    Game game;
    TextView timerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        String DifficulyLevel = getDifficultyLevel();
        game = new Game(DifficultyEnum.valueOf(DifficulyLevel));

        gridView = findViewById(R.id.grid_view);
        gridView.setNumColumns(game.getDifficulty().getValue());
        gridView.setBackgroundColor(Color.BLACK);
        gridView.setAdapter(new CellAdapter(game.getBoard(), getApplicationContext()));

        timerText = findViewById(R.id.timerText);

        // short click
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!game.getIsStarted()){
                    startTimer();
                }
                if(!game.getBoard().getCell(position).isClicked()) {
                    game.clickCell(position);
                    if(game.getBoard().getStatus() != StatusEnum.CONTINUE) {
                        finishedGame();
                    }
                    refreshView();
                }
            }
        });

        // long click - for put flag
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (!game.getIsStarted()){
                    startTimer();
                }
                if(!game.getBoard().getCell(position).isClicked()) {
                    game.getBoard().getCell(position).setFlaged(true);
                    refreshView();
                }
                return true;
            }
        });

    }

    public void startTimer(){
        customHandler.postDelayed(updateTimerThread, 0);
    }
    private Handler customHandler = new Handler();
    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timerText.setText(writeTimeClockFormat((int)game.getTime()));
            customHandler.postDelayed(this, 0);
        }
    };

    private String writeTimeClockFormat(int time) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(time), TimeUnit.MILLISECONDS.toSeconds(time) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
    }

    private String getDifficultyLevel() {
        Intent intent = getIntent();
        String key = String.format("%d", R.string.diff_tag);
        String value = intent.getStringExtra(key); //if it's a string you stored.
        return value;
    }

    private void finishedGame() {
        saveResult();
        sendResultParametersToEndActivity();
    }

    private void sendResultParametersToEndActivity() {
        Intent myIntent = new Intent(MainActivity.this, EndActivity.class);
        myIntent.putExtra(""+String.format("%d", R.string.status_tag), ""+game.getBoard().getStatus());
        myIntent.putExtra(""+String.format("%d", R.string.time_tag), ""+game.getResultTime());
        myIntent.putExtra(""+String.format("%d", R.string.diff_tag) , ""+game.getDifficulty());
        MainActivity.this.startActivity(myIntent);
    }

    private void saveResult() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(getString(R.string.easy_score_tag), this.game.EASY_BEST_TIME);
        editor.putLong(getString(R.string.med_score_tag), this.game.MEDIUM_BEST_TIME);
        editor.putLong(getString(R.string.hard_score_tag), this.game.HARD_BEST_TIME);
        editor.apply();
    }

    private void refreshView() {
        refreshGridView();
    }

    private void refreshGridView() {
        ((CellAdapter) gridView.getAdapter()).notifyDataSetChanged();
    }

}