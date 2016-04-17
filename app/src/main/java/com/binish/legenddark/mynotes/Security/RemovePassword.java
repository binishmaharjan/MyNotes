package com.binish.legenddark.mynotes.Security;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.binish.legenddark.mynotes.Adapters.RemovePasswordListener;
import com.binish.legenddark.mynotes.R;

/**
 * Created by legenddark on 2016/04/17.
 */
public class RemovePassword extends DialogFragment {

    Button mYes;
    Button mCancel;
    private RemovePasswordListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Confirm...");
        return  inflater.inflate(R.layout.remove_password_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mYes= (Button) view.findViewById(R.id.remove_enter);
        mCancel = (Button) view.findViewById(R.id.remove_cancel);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle argument = getArguments();
                if(argument != null && mListener != null){
                    int postion = argument.getInt("POSITION");
                    mListener.removePassword(postion);
                }

                dismiss();
            }
        });
    }

    public void setRemovePasswordListener(RemovePasswordListener mRemovePasswordListener) {
        mListener = mRemovePasswordListener;
    }
}
