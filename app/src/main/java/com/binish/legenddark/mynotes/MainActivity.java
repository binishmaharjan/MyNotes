package com.binish.legenddark.mynotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.binish.legenddark.mynotes.Adapters.AdapterNote;
import com.binish.legenddark.mynotes.Adapters.ButtonListener;
import com.binish.legenddark.mynotes.Adapters.ClickedDialogListener;
import com.binish.legenddark.mynotes.Adapters.Divider;
import com.binish.legenddark.mynotes.Adapters.SimpleTouchCallBack;
import com.binish.legenddark.mynotes.Database.Note;
import com.binish.legenddark.mynotes.Security.Password;
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

    private ButtonListener mAddListerner =new ButtonListener() {
        @Override
        public void add() {
         showDialog();
        }
    };

    private ClickedDialogListener mItemClicked = new ClickedDialogListener() {
        @Override
        public void onEachItemClicked(int position,View view) {
            showClickedDialog(position,view);
        }
    };

    private void showDialog() {
        DialogAdd dialogAdd = new DialogAdd();
        dialogAdd.show(getSupportFragmentManager(),"Add");
    }


    private void showClickedDialog(int position,View view){
        int viewId = view.getId();
        switch (viewId){
            case(R.id.row_title):
                DialogClicked dialogClicked = new DialogClicked();
                Bundle bundle = new Bundle();
                bundle.putInt("POSITION", position);

                Note note = (Note) mResults.get(position);
                String title = note.getmTitle();
                String description = note.getmDescription();

                bundle.putString("TITLE",title);
                bundle.putString("DESCRIP",description);

                dialogClicked.setArguments(bundle);
                dialogClicked.show(getSupportFragmentManager(),"Clicked");

                break;
            case (R.id.row_reminder):
                break;
            case (R.id.row_password):
                Password password = new Password();
                password.show(getSupportFragmentManager(),"Password");
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

        mAdapter = new AdapterNote(this,mResults,mRealm);
        mAdapter.setButtonLisener(mAddListerner);
        mAdapter.setItemClickedListener(mItemClicked);
        mAdapter.setHasStableIds(true);

        mRecyclerView = (NoteRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.hideIfEmpty(mToolbar);
        mRecyclerView.showIfEmpty(mEmptyView);
        mRecyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));


        SimpleTouchCallBack callBack =  new SimpleTouchCallBack(mAdapter);
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
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showDialog();
        return super.onOptionsItemSelected(item);
    }
}
