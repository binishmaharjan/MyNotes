package com.binish.legenddark.mynotes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.binish.legenddark.mynotes.Database.Note;

import io.realm.Realm;


/**
 * Created by legenddark on 2016/04/02.
 */
public class DialogAdd extends DialogFragment {


    EditText mTitle;
    EditText mDescription;
    Button mAdd;
    Button mCancel;
    
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id){
                case R.id.add_cancel:{
                    break;
                }
                
                case R.id.add_add:{
                    addAction();
                    break;
                }
            }
            dismiss();
        }
    };

    private void addAction() {
        String mNoteTitle =mTitle.getText().toString();
        String mNoteDescription = mDescription.getText().toString();
        Realm realm = Realm.getDefaultInstance();
        Note mNote = new Note(mNoteTitle,mNoteDescription,0, System.currentTimeMillis());
        realm.beginTransaction();
        realm.copyToRealm(mNote);
        realm.commitTransaction();
        realm.close();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.DialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_dialiog_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitle = (EditText) view.findViewById(R.id.add_title);
        mDescription = (EditText) view.findViewById(R.id.add_description);
        mAdd = (Button) view.findViewById(R.id.add_add);
        mCancel = (Button) view.findViewById(R.id.add_cancel);

        mAdd.setOnClickListener(mClickListener);
        mCancel.setOnClickListener(mClickListener);
    }
}
