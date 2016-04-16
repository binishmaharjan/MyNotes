package com.binish.legenddark.mynotes.Widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by legenddark on 2016/04/05.
 */
public class NoteRecyclerView extends RecyclerView {
    private List<View> mNonEmptyViews = Collections.emptyList();
    private List<View> mEmptyView = Collections.emptyList();


    private  AdapterDataObserver mObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            toggleViews();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            toggleViews();

        }
    };

    private void toggleViews() {
        if(getAdapter() !=null && !mEmptyView.isEmpty() && !mNonEmptyViews.isEmpty()){
            if(getAdapter().getItemCount() == 0){
                com.binish.legenddark.mynotes.Extra.Util.showViews(mEmptyView);

                setVisibility(View.GONE);

                com.binish.legenddark.mynotes.Extra.Util.hideViews(mNonEmptyViews);
            }
            else{
                com.binish.legenddark.mynotes.Extra.Util.showViews(mNonEmptyViews);

                setVisibility(View.VISIBLE);

                com.binish.legenddark.mynotes.Extra.Util.hideViews(mEmptyView);
            }
        }

    }


    public NoteRecyclerView(Context context) {
        super(context);
    }

    public NoteRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoteRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if(adapter != null){
            adapter.registerAdapterDataObserver(mObserver);
        }
        mObserver.onChanged();
    }

    public void hideIfEmpty(View... views) {
        mNonEmptyViews = Arrays.asList(views);

    }

    public void showIfEmpty(View...views) {
        mEmptyView = Arrays.asList(views);

    }
}
