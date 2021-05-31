package com.shacharnissan.minesweeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shacharnissan.minesweeper.logic.DifficultyEnum;
import com.shacharnissan.minesweeper.logic.Game;
import com.shacharnissan.minesweeper.logic.Score;
import com.shacharnissan.minesweeper.logic.StatusEnum;
import com.shacharnissan.minesweeper.logic.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    private Game game;
    TextView timerText;
    private List<Score> scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("main activity - onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        String DifficulyLevel = getDifficultyLevel();
        game = new Game(DifficultyEnum.valueOf(DifficulyLevel));

        gridView = findViewById(R.id.grid_view);
        gridView.setNumColumns(game.getDifficulty().getValue());
        gridView.setBackgroundColor(Color.BLACK);
        gridView.setAdapter(new CellAdapter(game.getBoard(), getApplicationContext()));

        timerText = findViewById(R.id.timerText);

        loadScoresJsonFileToGameResultsLists();

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

    private void loadScoresJsonFileToGameResultsLists() {
        String filename = getResources().getString(R.string.Scores_Json_File);
        SharedPreferences sharedPref = getSharedPreferences(filename,Context.MODE_PRIVATE);

        // for reset all highest results:
        // sharedPref.edit().clear().commit();

        String jsonFileString = sharedPref.getString(getString(R.string.Scores_Json_String),
                Utils.getJsonFromAssets(getApplicationContext(), "template_scores_json.json"));
        Gson gson = new Gson();
        Type listScoreType = new TypeToken<ArrayList<Score>>() {}.getType();
        scores = gson.fromJson(jsonFileString, listScoreType);
        convertScoresListToGameResultsLists();
    }

    private void convertScoresListToGameResultsLists() {
        int i;
        for (i = 0; i < Game.NUM_OF_RESULTS; i++) {
            game.best10resultsEasy.set(i, scores.get(i).convertStringTimeToLongMillisecondTime());
            game.best10resultsMedium.set(i, scores.get(i+10).convertStringTimeToLongMillisecondTime());
            game.best10resultsHard.set(i, scores.get(i+20).convertStringTimeToLongMillisecondTime());
        }
    }

    public void startTimer(){
        customHandler.postDelayed(updateTimerThread, 0);
    }
    public void stopTimer(){ customHandler.removeCallbacks(updateTimerThread); }

    // define timer:
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
        if (game.getBoard().getStatus() == StatusEnum.WIN)
            saveResult();
        sendResultParametersToEndActivity();
    }

    private void sendResultParametersToEndActivity() {
        Intent myIntent = new Intent(MainActivity.this, EndActivity.class);
        myIntent.putExtra(""+String.format("%d", R.string.status_tag), ""+game.getBoard().getStatus());
        myIntent.putExtra(""+String.format("%d", R.string.time_tag), ""+game.getResultTime());
        myIntent.putExtra(""+String.format("%d", R.string.diff_tag) , ""+game.getDifficulty());
        // myIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        MainActivity.this.startActivity(myIntent);
    }

    private void saveResult() {
        String filename = getResources().getString(R.string.score_filename);
        SharedPreferences sharedPref = getSharedPreferences(filename,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        switch (this.game.getDifficulty()){
            case EASY:
                editor.putLong(getString(R.string.easy_score_tag), Game.EASY_BEST_TIME);
                break;
            case MEDIUM:
                editor.putLong(getString(R.string.med_score_tag), Game.MEDIUM_BEST_TIME);
                break;
            case HARD:
                editor.putLong(getString(R.string.hard_score_tag), Game.HARD_BEST_TIME);
                break;
        }
        editor.apply();

        convertGameResultsListsToScoresList();
        saveHighestScoresListsToJsonFile();
    }

    private void convertGameResultsListsToScoresList() {
        Score s;

        for (int i = 0; i < Game.NUM_OF_RESULTS; i++) {
            s = new Score("" + (i+1),
                    "" + writeTimeClockFormat(game.best10resultsEasy.get(i)),
                    DifficultyEnum.EASY );
            if (game.best10resultsEasy.get(i) == Long.MAX_VALUE)
                s.setTimeScore("00:00");
            scores.set(i, s);
        }
        for (int i = 0; i < Game.NUM_OF_RESULTS; i++) {
            s = new Score("" + (i+1),
                    "" + writeTimeClockFormat(game.best10resultsMedium.get(i)),
                    DifficultyEnum.MEDIUM );
            if (game.best10resultsMedium.get(i) == Long.MAX_VALUE)
                s.setTimeScore("00:00");
            scores.set(i+10 , s);
        }
        for (int i = 0; i < Game.NUM_OF_RESULTS; i++) {
            s = new Score("" + (i+1),
                    "" + writeTimeClockFormat(game.best10resultsHard.get(i)),
                    DifficultyEnum.HARD );
            if (game.best10resultsHard.get(i) == Long.MAX_VALUE)
                s.setTimeScore("00:00");
            scores.set(i+20, s);
        }
    }

    private void saveHighestScoresListsToJsonFile() {
        String filename = getResources().getString(R.string.Scores_Json_File);
        SharedPreferences sharedPref = getSharedPreferences(filename,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();
        Type listScoreType = new TypeToken<List<Score>>() {}.getType();
        String scoresListJson = gson.toJson(scores, listScoreType);

        editor.putString(getString(R.string.Scores_Json_String), scoresListJson);
        editor.apply();
    }

    private void refreshView() {
        refreshGridView();
    }

    private void refreshGridView() {
        ((CellAdapter) gridView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        System.out.println("main activity - onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        System.out.println("main activity - onPause");
        super.onPause();
        stopTimer();
    }

    private String writeTimeClockFormat(long time) {
        String newTime = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(time), TimeUnit.MILLISECONDS.toSeconds(time) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
        return newTime;
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("main activity - onStop");
    }
}