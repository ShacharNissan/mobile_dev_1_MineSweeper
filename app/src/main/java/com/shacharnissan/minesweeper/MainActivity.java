package com.shacharnissan.minesweeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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

        game = new Game(DifficultyEnum.EASY);

        gridView = findViewById(R.id.grid_view);
        gridView.setNumColumns(game.getDifficulty().getValue());
        gridView.setBackgroundColor(Color.BLACK);
        gridView.setAdapter(new CellAdapter(game.getBoard(), getApplicationContext()));

    }

}