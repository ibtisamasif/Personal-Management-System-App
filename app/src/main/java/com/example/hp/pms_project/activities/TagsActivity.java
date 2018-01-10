package com.example.hp.pms_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.hp.pms_project.R;
import com.example.hp.pms_project.adapter.RealmAddTagsAdapter;
import com.example.hp.pms_project.adapter.TagsAdapter;
import com.example.hp.pms_project.model.addTags;
import com.example.hp.pms_project.realm.RealmController;
import com.example.hp.pms_project.utils.Prefs;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class TagsActivity extends AppCompatActivity {

    private Button btnAddTag;
    private Button btnAddTxn;
    private RecyclerView recycler;
    private Realm realm;
    private TagsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnAddTag = (Button) findViewById(R.id.btnAddTag);
        btnAddTxn = (Button) findViewById(R.id.btnAddTxn);
        recycler = (RecyclerView) findViewById(R.id.recycler);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        this.realm = RealmController.with(this).getRealm();
        setupRecycler();

        if (!Prefs.with(this).getPreLoad()) {
            setRealmData();
        }
        // refresh the realm instance
        RealmController.with(this).refresh();
        setRealmAdapter(RealmController.with(this).getTransactionsTags());

        btnAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddTagsActivity.class);
                startActivity(intent);

            }
        });

        btnAddTxn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
                startActivity(intent);
            }
        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
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