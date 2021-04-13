package com.shacharnissan.minesweeper;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CellView extends LinearLayout {
    ImageView imageview;
    public CellView(Context context) {
        super(context);

        this.setOrientation(VERTICAL);

        imageview = new ImageView(context);

        LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageview.setLayoutParams(layoutParams);

        //imageview.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        //imageview.setForegroundGravity(Gravity.CENTER_VERTICAL);
        //imageview.setTextSize(50);
        //imageview.setTextColor(Color.BLACK);
        //setBackground(getResources().getDrawable(R.drawable.mine,null));

        addView(imageview);

    }
}
