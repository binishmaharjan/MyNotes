package com.binish.legenddark.mynotes.Security;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.binish.legenddark.mynotes.Adapters.ApplyPasswordListener;
import com.binish.legenddark.mynotes.Adapters.RemovePasswordListener;
import com.binish.legenddark.mynotes.R;

/**
 * Created by legenddark on 2016/04/23.
 */
public class ApplyPassword extends DialogFragment{
    Button mYes;
    Button mCancel;
    private ApplyPasswordListener mListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Confirm...");
        return  inflater.inflate(R.layout.apply_password_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mYes= (Button) view.findViewById(R.id.apply_enter);
        mCancel = (Button) view.findViewById(R.id.apply_cancel);

        mYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle argument = getArguments();
                if (mListener != null && argument != null){
                   int position = argument.getInt("POSITION");
                    mListener.applyPassword(position);
                }
                dismiss();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    public void setApplyPasswordListener(ApplyPasswordListener mApplyPasswordListener) {
        mListener = mApplyPasswordListener;
    }
}


