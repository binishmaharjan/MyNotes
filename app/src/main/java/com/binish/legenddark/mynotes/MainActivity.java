package com.binish.legenddark.mynotes;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.binish.legenddark.mynotes.Adapters.AdapterNote;
import com.binish.legenddark.mynotes.Adapters.ApplyPasswordListener;
import com.binish.legenddark.mynotes.Adapters.ButtonListener;
import com.binish.legenddark.mynotes.Adapters.ClickedDialogListener;
import com.binish.legenddark.mynotes.Adapters.ConfirmPasswordListener;
import com.binish.legenddark.mynotes.Adapters.Divider;
import com.binish.legenddark.mynotes.Adapters.PasswordListener;
import com.binish.legenddark.mynotes.Adapters.RemovePasswordListener;
import com.binish.legenddark.mynotes.Adapters.SimpleTouchCallBack;
import com.binish.legenddark.mynotes.Database.Note;
import com.binish.legenddark.mynotes.Security.ApplyPassword;
import com.binish.legenddark.mynotes.Security.ConfirmPassword;
import com.binish.legenddark.mynotes.Security.Password;
import com.binish.legenddark.mynotes.Security.RemovePassword;
import com.binish.legenddark.mynotes.Widget.NoteRecyclerView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;
    Button mAdd;
    NoteRecyclerView mRecyclerView;
    Realm mRealm;
    RealmResults<Note> mResults;
    AdapterNote mAdapter;
    View mEmptyView;

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDialog();
        }
    };

    private RealmChangeListener mChangeListener = new RealmChangeListener() {
        @Override
        public void onChange() {
            mAdapter.update(mResults);

        }
    };

    private ButtonListener mAddListerner = new ButtonListener() {
        @Override
        public void add() {
            showDialog();
        }
    };

    private ClickedDialogListener mItemClicked = new ClickedDialogListener() {
        @Override
        public void onEachItemClicked(int position, View view) {
            showClickedDialog(position, view);
        }
    };


    private PasswordListener mPasswordListener = new PasswordListener() {
        @Override
        public void setPassword(String password) {
            SharedPreferences pref = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("PASSWORD", password);
            editor.apply();
            mAdapter.setMasterPassword(getMasterPassword());
            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
        }

    };

    RemovePasswordListener mRemovePasswordListener = new RemovePasswordListener() {
        @Override
        public void removePassword(int postion) {
            mAdapter.removePasswordFromAdapter(postion);
        }
    };

    ApplyPasswordListener mApplyPasswordListener = new ApplyPasswordListener() {
        @Override
        public void applyPassword(int position) {
            mAdapter.setPasswordFromAdpater(position);
        }
    };


    private void showDialog() {
        DialogAdd dialogAdd = new DialogAdd();
        dialogAdd.show(getSupportFragmentManager(), "Add");
    }

    public String getMasterPassword() {

        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        String mPassword = pref.getString("PASSWORD", "");
        return mPassword;

    }


    private void showClickedDialog(int position, View view) {
        int viewId = view.getId();
        final Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        Note note = (Note) mResults.get(position);
        String title = note.getmTitle();
        String description = note.getmDescription();
        bundle.putString("TITLE", title);
        bundle.putString("DESCRIP", description);


        final String mPassword = getMasterPassword();

        switch (viewId) {
            case (R.id.row_title):
                if (note.isHasPassword()) {
                    ConfirmPasswordListener mConfirmPasswordListener = new ConfirmPasswordListener() {
                        @Override
                        public void confirmPassword(String confirm) {
                            if (confirm.equals(mPassword)) {
                                DialogClicked dialogClicked = new DialogClicked();
                                dialogClicked.setArguments(bundle);
                                dialogClicked.show(getSupportFragmentManager(), "Clicked");

                            } else {
                                Toast.makeText(MainActivity.this, "Password doesnot match", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    ConfirmPassword confirmPassword = new ConfirmPassword();
                    confirmPassword.show(getSupportFragmentManager(), "Confirm");
                    confirmPassword.setConfirmPasswordListener(mConfirmPasswordListener);


                } else {

                    DialogClicked dialogClicked = new DialogClicked();
                    dialogClicked.setArguments(bundle);
                    dialogClicked.show(getSupportFragmentManager(), "Clicked");
                }
                Toast.makeText(MainActivity.this, note.getmPassword(), Toast.LENGTH_SHORT).show();
                break;
            case (R.id.row_reminder):

                break;
            case (R.id.row_password):
                if (!note.isHasPassword()) {
                    if(!getMasterPassword().equals("")){
                        ApplyPassword applyPassword = new ApplyPassword();
                        applyPassword.show(getSupportFragmentManager(), "Apply");
                        applyPassword.setArguments(bundle);
                        applyPassword.setApplyPasswordListener(mApplyPasswordListener);

                    }
                    else{
                        Toast.makeText(MainActivity.this, "The is no Password Set", Toast.LENGTH_SHORT).show();
                    }
                    
                } else {
                    ConfirmPasswordListener mConfirmPasswordListener = new ConfirmPasswordListener() {
                        @Override
                        public void confirmPassword(String confirm) {
                            if (confirm.equals(mPassword)) {
                                RemovePassword removePassword = new RemovePassword();
                                removePassword.show(getSupportFragmentManager(), "Remove");
                                removePassword.setArguments(bundle);
                                removePassword.setRemovePasswordListener(mRemovePasswordListener);

                            } else {
                                Toast.makeText(MainActivity.this, "Password doesnot match", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    ConfirmPassword confirmPassword = new ConfirmPassword();
                    confirmPassword.show(getSupportFragmentManager(), "Confirm");
                    confirmPassword.setConfirmPasswordListener(mConfirmPasswordListener);


                }

                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyView = findViewById(R.id.empty_view);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mAdd = (Button) findViewById(R.id.empty_view_add);
        mAdd.setOnClickListener(mClickListener);

        mRealm = Realm.getDefaultInstance();

        mResults = mRealm.where(Note.class).findAllAsync();

        LinearLayoutManager manager = new LinearLayoutManager(this);

        mAdapter = new AdapterNote(this, mResults, mRealm,getSupportFragmentManager());
        mAdapter.setButtonLisener(mAddListerner);
        mAdapter.setItemClickedListener(mItemClicked);
        mAdapter.setHasStableIds(true);
        mAdapter.setMasterPassword(getMasterPassword());


        mRecyclerView = (NoteRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.hideIfEmpty(mToolbar);
        mRecyclerView.showIfEmpty(mEmptyView);
        mRecyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));


        SimpleTouchCallBack callBack = new SimpleTouchCallBack(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callBack);
        helper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mResults.addChangeListener(mChangeListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mResults.removeChangeListener(mChangeListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            showDialog();
        }
        else if (item.getItemId() == R.id.action_set_password) {
            if (getMasterPassword().equals("")) {
                Password password = new Password();
                password.show(getSupportFragmentManager(), "Password");
                password.setPasswordListener(mPasswordListener);

            } else {
                ConfirmPasswordListener mConfirmPasswordListener = new ConfirmPasswordListener() {
                    @Override
                    public void confirmPassword(String confirm) {
                        if (confirm.equals(getMasterPassword())) {
                            Password password = new Password();
                            password.show(getSupportFragmentManager(), "Password");
                            password.setPasswordListener(mPasswordListener);

                        } else {
                            Toast.makeText(MainActivity.this, "Password doesnot match", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                ConfirmPassword confirmPassword = new ConfirmPassword();
                confirmPassword.show(getSupportFragmentManager(), "Confirm");
                confirmPassword.setConfirmPasswordListener(mConfirmPasswordListener);


            }
        }

        return super.onOptionsItemSelected(item);
    }
}
