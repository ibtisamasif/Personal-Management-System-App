package com.example.hp.pms_project.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hp.pms_project.R;
import com.example.hp.pms_project.adapter.RealmAddTagsAdapter;
import com.example.hp.pms_project.adapter.TagsAdapter;
import com.example.hp.pms_project.model.addTags;
import com.example.hp.pms_project.realm.RealmController;
import com.example.hp.pms_project.utils.Prefs;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddTagsActivity extends AppCompatActivity {

    private EditText etAddTag;
    private Button btnSaveTag;
    private RecyclerView recycler;
    private Realm realm;
    private TagsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tags);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        etAddTag = (EditText) findViewById(R.id.etAddTag);
        btnSaveTag = (Button) findViewById(R.id.btnSaveTag);
        setSupportActionBar(toolbar);
        this.realm = RealmController.with(this).getRealm();
        setupRecycler();

        if (!Prefs.with(this).getPreLoad()) {
            setRealmData();
        }
        // refresh the realm instance
        RealmController.with(this).refresh();
        setRealmAdapter(RealmController.with(this).getTransactionsTags());

        btnSaveTag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {
                        addTags add = bgRealm.createObject(addTags.class);
                        add.setId(System.currentTimeMillis());
                        add.setTagName(etAddTag.getText().toString());
                    }
                });
                Log.d("AddTagsActivity", "onCreate: " + realm.where(addTags.class).findAll());
                realm.close();
                btnSaveTag.setVisibility(View.GONE);
                finish();


            }
        });


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    public void setRealmAdapter(RealmResults<addTags> add) {

        RealmAddTagsAdapter realmAdapter = new RealmAddTagsAdapter(this, add, true);
        // Set the data and tell the RecyclerView to draw
        adapter.setRealmAdapter(realmAdapter);
        adapter.notifyDataSetChanged();
    }

    private void setupRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler.setHasFixedSize(true);
        recycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        // create an empty adapter and add it to the recycler view
        adapter = new TagsAdapter(this);
        recycler.setAdapter(adapter);

        RealmController.with(this).refresh();
        setRealmAdapter(RealmController.with(this).getSortTags());
    }

    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
    private void setRealmData() {
        ArrayList<addTags> addTags = new ArrayList<>();
        for (addTags t : addTags) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(t);
            realm.commitTransaction();
        }
        Prefs.with(this).setPreLoad(true);
    }

}
