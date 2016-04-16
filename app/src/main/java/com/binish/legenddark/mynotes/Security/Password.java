package com.binish.legenddark.mynotes.Security;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.binish.legenddark.mynotes.R;

/**
 * Created by legenddark on 2016/04/16.
 */
public class Password extends DialogFragment {

    EditText mPassword;
    EditText mConfirmPassword;
    Button mEnter;
    Button mCancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Set Password");
        return inflater.inflate(R.layout.password_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPassword = (EditText) view.findViewById(R.id.password_input);
        mConfirmPassword = (EditText) view.findViewById(R.id.password_confirm);
        mEnter = (Button) view.findViewById(R.id.password_enter);
        mCancel = (Button) view.findViewById(R.id.password_cancel);
    }
}
