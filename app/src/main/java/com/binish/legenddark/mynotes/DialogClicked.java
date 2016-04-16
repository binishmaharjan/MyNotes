package com.binish.legenddark.mynotes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by legenddark on 2016/04/09.
 */
public class DialogClicked extends DialogFragment {

    TextView mTitle;
    TextView mDescription;
    Button mClose;

    private View.OnClickListener mBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.clicked_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitle = (TextView) view.findViewById(R.id.clicked_title);
        mDescription = (TextView) view.findViewById(R.id.clicked_description);
        mClose = (Button) view.findViewById(R.id.clicked_button);
        mClose.setOnClickListener(mBtnListener);

        Bundle arguments = getArguments();
        if (arguments != null){
            int position = arguments.getInt("POSITION");
            String title = arguments.getString("TITLE");
            String description = arguments.getString("DESCRIP");

            mTitle.setText(title);
            mDescription.setText(description);
        }


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.DialogTheme);
    }
}
