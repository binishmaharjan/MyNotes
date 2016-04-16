package com.binish.legenddark.mynotes.Adapters;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by legenddark on 2016/04/05.
 */
public class Divider extends RecyclerView.ItemDecoration {
    int mOrientation;
    public Divider(Context context,int orientation){
        mOrientation =orientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(mOrientation == LinearLayoutManager.VERTICAL){
            outRect.set(0,0,0,10);
        }
    }
}
