package com.binish.legenddark.mynotes.Security;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.binish.legenddark.mynotes.Adapters.ConfirmPasswordListener;
import com.binish.legenddark.mynotes.R;

/**
 * Created by legenddark on 2016/04/17.
 */
public class ConfirmPassword extends DialogFragment {

    Button mEnter;
    Button mCancel;
    EditText mConfirm;
    private ConfirmPasswordListener mListener;
    boolean mCheckForConfirmation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Enter Password");
        return inflater.inflate(R.layout.confirm_password_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEnter= (Button) view.findViewById(R.id.confirm_enter);
        mCancel= (Button) view.findViewById(R.id.confirm_cancel);
        mConfirm = (EditText) view.findViewById(R.id.confirm_password);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mListener != null){
                    String confirm = mConfirm.getText().toString();
                    mListener.confirmPassword(confirm);
                }
                dismiss();
            }
        });
    }

    public void setConfirmPasswordListener(ConfirmPasswordListener mConfirmPasswordListener) {
        mListener = mConfirmPasswordListener;
    }
}
