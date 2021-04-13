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
                    if(game.clickCell(position))
                        //game won / lose
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

    private void refreshView() {
        refreshGridView();
    }

    private void refreshGridView() {
        ((CellAdapter) gridView.getAdapter()).notifyDataSetChanged();
    }

}