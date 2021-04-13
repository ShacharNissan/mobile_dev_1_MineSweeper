package com.shacharnissan.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CellView extends LinearLayout {

    ImageView imageview;
    TextView textView;

    public CellView(Context context) {
        super(context);

        this.setOrientation(VERTICAL);

        imageview = new ImageView(context);
        textView = new TextView(context);

        LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageview.setLayoutParams(layoutParams);
        textView.setLayoutParams(layoutParams);

        textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        textView.setForegroundGravity(Gravity.CENTER_VERTICAL);
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);
        //setBackground(getResources().getDrawable(R.drawable.mine,null));
        setBackgroundColor(Color.GRAY);
        addView(textView);
        addView(imageview);
    }
}
