package com.shacharnissan.minesweeper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.shacharnissan.minesweeper.logic.Board;
import com.shacharnissan.minesweeper.logic.Game;

public class CellAdapter extends BaseAdapter {
    private Board board;
    private Context context;

    private int cachedWidth;
    private int cachedHeight;

    public CellAdapter(Board board, Context context){
        this.board = board;
        this.context = context;
        this.cachedHeight = -1;
        this.cachedWidth = -1;
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
        CellView cell = null;

        if(convertView != null)
            cell = (CellView)convertView;
        else
            cell = new CellView(context);

        GridView gridView = (GridView)parent;

        if(cachedHeight <= 0) {
            calculateSizes(gridView);
        }

        ViewGroup.LayoutParams layoutParams = new GridView.LayoutParams(cachedWidth,cachedHeight);

        cell.setLayoutParams(layoutParams);

        if(board.getCell(position).isClicked()) {
            switch (board.getCell(position).getType()) {
                case MINE:
                    cell.imageview.setImageDrawable(context.getDrawable(R.drawable.mine));
                    cell.textView.setVisibility(View.GONE);
                    cell.imageview.setVisibility(View.VISIBLE);

                    break;
                case NUMBER:
                    cell.textView.setVisibility(View.VISIBLE);
                    cell.imageview.setVisibility(View.GONE);
                    cell.textView.setText("" + board.getCell(position).getNearMines());
                    break;
                case WRONG_FLAG:
                    cell.imageview.setImageDrawable(context.getDrawable(R.drawable.wrong_flag));
                    cell.imageview.setVisibility(View.VISIBLE);
                    cell.textView.setVisibility(View.INVISIBLE);
                    break;
                case MINE_CLICKED:
                    cell.imageview.setImageDrawable(context.getDrawable(R.drawable.mine_clicked));
                    cell.imageview.setVisibility(View.VISIBLE);
                    cell.textView.setVisibility(View.INVISIBLE);
                    break;
            }
        }

        if(board.getCell(position).isFlaged()){
            cell.imageview.setImageDrawable(context.getDrawable(R.drawable.flag));
            cell.textView.setVisibility(View.GONE);
            cell.imageview.setVisibility(View.VISIBLE);
        }

        return cell;
    }

    private void calculateSizes(GridView gridView) {

        int hSpacing =  gridView.getHorizontalSpacing();
        int vSpacing =  gridView.getVerticalSpacing();

        int columns = gridView.getNumColumns();
        int rows = board.getBoardSize() / columns;

        int hPaddingSize = hSpacing * (columns - 1);
        int vPaddingSize = vSpacing * (rows - 1);

        cachedWidth = (gridView.getWidth() - hPaddingSize) / columns;


        cachedHeight = (gridView.getHeight() - vPaddingSize) / rows;

    }
}
