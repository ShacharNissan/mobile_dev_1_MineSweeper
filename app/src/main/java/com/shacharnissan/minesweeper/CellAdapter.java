package com.shacharnissan.minesweeper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.shacharnissan.minesweeper.logic.Board;
import com.shacharnissan.minesweeper.logic.Game;

public class CellAdapter extends BaseAdapter {
    private Board board;
    private Context context;

    public CellAdapter(Board board, Context context){
        this.board = board;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.board.getBoardSize();
    }

    @Override
    public Object getItem(int position) {
        return board.getCell(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
