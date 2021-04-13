package com.shacharnissan.minesweeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.shacharnissan.minesweeper.logic.Board;
import com.shacharnissan.minesweeper.logic.DifficultyEnum;
import com.shacharnissan.minesweeper.logic.Game;
import com.shacharnissan.minesweeper.logic.StatusEnum;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        game = new Game(DifficultyEnum.HARD);

        gridView = findViewById(R.id.grid_view);
        gridView.setNumColumns(game.getDifficulty().getValue());
        gridView.setBackgroundColor(Color.BLACK);
        gridView.setAdapter(new CellAdapter(game.getBoard(), getApplicationContext()));

        // short click
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!game.getBoard().getCell(position).isClicked()) {
                    game.clickCell(position);
                    if(game.getBoard().getStatus() != StatusEnum.CONTINUE) {
                        finishedGame();
                    }
                    refreshView();
                }
            }
        });

        // long click - for flag
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(!game.getBoard().getCell(position).isClicked()) {
                    game.getBoard().getCell(position).setFlaged(true);
                    refreshView();
                }
                return true;
            }
        });

    }

    private void finishedGame() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(getString(R.string.easy_score_tag), this.game.EASY_BEST_TIME);
        editor.putLong(getString(R.string.med_score_tag), this.game.MEDIUM_BEST_TIME);
        editor.putLong(getString(R.string.hard_score_tag), this.game.HARD_BEST_TIME);
        editor.apply();

        Intent myIntent = new Intent(MainActivity.this, EndActivity.class);
        myIntent.putExtra(String.format("%d", R.string.status_tag), game.getBoard().getStatus());
        myIntent.putExtra(String.format("%d", R.string.time_tag), game.getResultTime());
        MainActivity.this.startActivity(myIntent);

    }

    private void refreshView() {
        refreshGridView();
    }

    private void refreshGridView() {
        ((CellAdapter) gridView.getAdapter()).notifyDataSetChanged();
    }

}