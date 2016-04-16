package com.binish.legenddark.mynotes.Extra;

import android.view.View;

import java.util.List;

/**
 * Created by legenddark on 2016/04/05.
 */
public class Util {
    public static void showViews(List<View> views){
        for(View view:views){
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hideViews(List<View> views){
        for(View view :views){
            view.setVisibility(View.GONE);
        }
    }
}
