package com.binish.legenddark.mynotes.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.binish.legenddark.mynotes.Database.Note;
import com.binish.legenddark.mynotes.MainActivity;
import com.binish.legenddark.mynotes.R;
import com.binish.legenddark.mynotes.Security.ConfirmPassword;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by legenddark on 2016/04/05.
 */
public class AdapterNote extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener{
    LayoutInflater mInflater;
    public static int FOOTER = 1;
    public static final int ITEM = 0;
    RealmResults<Note> mResults;
    ButtonListener mAddListener;
    ClickedDialogListener mItemClickedListener;
    Realm mRealm;
    Context mContext;
    FragmentManager mFragmentManager;

    public void update(RealmResults result) {
        mResults = result;
        notifyDataSetChanged();
    }

    public void setButtonLisener(ButtonListener listener){
        mAddListener = listener;
    }

    public  void  setItemClickedListener(ClickedDialogListener listener){
        mItemClickedListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (mResults == null || position < mResults.size()) {
            return ITEM;
        } else {
            return FOOTER;
        }

    }

    public AdapterNote(Context context, RealmResults results,Realm realm,FragmentManager manager) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        update(results);
        mRealm = realm;
        mFragmentManager = manager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == FOOTER){

            View view = mInflater.inflate(R.layout.footer, parent, false);
             return new FooterHolder(view);
        }else{
            View view = mInflater.inflate(R.layout.single_row, parent, false);
             return new NoteHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if( holder instanceof NoteHolder){
            NoteHolder noteHolder = (NoteHolder) holder;
            Note note = (Note) mResults.get(position);
            noteHolder.setTitle(note.getmTitle());
            noteHolder.setSecurityIcon(note.isHasPassword());
        }

    }


    @Override
    public int getItemCount() {
        if(mResults == null ||mResults.isEmpty()){
            return 0;
        }else{
            return mResults.size() +1;
        }

    }

    @Override
    public long getItemId(int position) {
        if(position < mResults.size()){
            return mResults.get(position).getmTimeAdded();
        }
        return RecyclerView.NO_ID;
    }

    String masterPassword;

    public void setMasterPassword(String password){
        masterPassword = password;
    }

    @Override

    public void onSwipe(final int position) {
        if(position < mResults.size()){
            if(mResults.get(position).isHasPassword()){
                ConfirmPasswordListener confirmPasswordListener = new ConfirmPasswordListener() {
                    @Override
                    public void confirmPassword(String confirm) {
                        if(confirm.equals(masterPassword)){
                            mRealm.beginTransaction();
                            mResults.get(position).removeFromRealm();
                            mRealm.commitTransaction();
                            notifyItemRemoved(position);
                        }

                    }
                };

                ConfirmPassword confirmPassword = new ConfirmPassword();
                confirmPassword.show(mFragmentManager,"Confirm");
                confirmPassword.setConfirmPasswordListener(confirmPasswordListener);
            }
            else{
                mRealm.beginTransaction();
                mResults.get(position).removeFromRealm();
                mRealm.commitTransaction();
                notifyItemRemoved(position);
            }

        }

    }

    public void setPasswordFromAdpater(int position) {
        mRealm.beginTransaction();
        mResults.get(position).setHasPassword(true);
        mRealm.commitTransaction();
        Toast.makeText(mContext, "Password is set", Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }

    public void removePasswordFromAdapter(int postion) {
        mRealm.beginTransaction();
        mResults.get(postion).setHasPassword(false);
        mRealm.commitTransaction();
        Toast.makeText(mContext, "Password removed", Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }

    class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTitle;
        ImageView mAlram;
        ImageView mPassword;


        public NoteHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mTitle = (TextView) itemView.findViewById(R.id.row_title);
            mAlram = (ImageView) itemView.findViewById(R.id.row_reminder);
            mPassword = (ImageView) itemView.findViewById(R.id.row_password);
            mTitle.setOnClickListener(this);
            mAlram.setOnClickListener(this);
            mPassword.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickedListener.onEachItemClicked(getAdapterPosition(), v);
        }

        public void setTitle(String s) {
            mTitle.setText(s);
        }

        public void setSecurityIcon(boolean hasPassword) {
            Drawable drawable;
            if (hasPassword){
               drawable =  ContextCompat.getDrawable(mContext,R.drawable.ic_action_shield);
            }else{
                drawable =ContextCompat.getDrawable(mContext,R.drawable.ic_action_shield_off);
            }
            mPassword.setImageDrawable(drawable);

        }
    }

    class FooterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        Button mAdd;

        public FooterHolder(View itemView) {
            super(itemView);
            mAdd = (Button) itemView.findViewById(R.id.footer_add_add);
            mAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mAddListener.add();
        }
    }
}
