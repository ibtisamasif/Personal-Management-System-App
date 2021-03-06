package com.example.hp.pms_project.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.pms_project.R;
import com.example.hp.pms_project.adapter.IncExpBudAdapter;
import com.example.hp.pms_project.adapter.RealmTransactionsAdapter;
import com.example.hp.pms_project.adapter.TransactionsAdapter;
import com.example.hp.pms_project.model.addTags;
import com.example.hp.pms_project.model.transactionTable;
import com.example.hp.pms_project.realm.RealmController;
import com.example.hp.pms_project.utils.Prefs;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class SelectTagsTotalActivity extends AppCompatActivity {
    public ArrayList<String> tagsCategory;
    Spinner spTags;
    private String addTagsToTransactionTable = "";
    private IncExpBudAdapter adapter;
    private RecyclerView recycler;
    private Realm realm;
    private TextView tvSumTags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tags_total);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        tvSumTags = (TextView) findViewById(R.id.tvSumTags);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        spTags = (Spinner) findViewById(R.id.spTags);
        tagsCategory = new ArrayList<String>();
        showAllTags();

        this.realm = RealmController.with(this).getRealm();
        setupRecycler();

        if (!Prefs.with(this).getPreLoad()) {
            setRealmData();
        }
        // refresh the realm instance
        RealmController.with(this).refresh();

    }


    public void setRealmAdapter(RealmResults<transactionTable> transactions) {

        RealmTransactionsAdapter realmAdapter = new RealmTransactionsAdapter(this.getApplicationContext(), transactions, true);
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
        adapter = new IncExpBudAdapter(this);
        recycler.setAdapter(adapter);
        RealmController.with(this).refresh();
        setRealmAdapter(RealmController.with(this).getSort());
    }

    private void setRealmData() {
        ArrayList<transactionTable> transactionsArrayList = new ArrayList<>();
        for (com.example.hp.pms_project.model.transactionTable t : transactionsArrayList) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(t);
            realm.commitTransaction();
        }
        Prefs.with(this).setPreLoad(true);
    }


    public void showAllTags() {
        Realm realm = Realm.getDefaultInstance();
        tagsCategory.add("default");
        RealmQuery<addTags> queryAllTags = realm.where(addTags.class);
//        queryAllTags.equalTo("type", "Expense");
        RealmResults<addTags> manyAddTags = queryAllTags.findAll();
        for (addTags addTags : manyAddTags) {
            //  Log.d("NavigationDrawer", "onCreate: " + addTags.getTagName());
            tagsCategory.add(addTags.getTagName());

        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tagsCategory);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTags.setAdapter(dataAdapter);
        spTags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                addTagsToTransactionTable = String.valueOf(spTags.getSelectedItem());
                setRealmAdapter(RealmController.with(getApplication()).getTransactionsTages(addTagsToTransactionTable));
                long sum = 0;
                Realm realm = Realm.getDefaultInstance();
                RealmQuery<transactionTable> queryExpance = realm.where(transactionTable.class);
                queryExpance.equalTo("tagName", addTagsToTransactionTable);
                RealmResults<transactionTable> manyExpance = queryExpance.findAll();
                for (transactionTable transactionTable : manyExpance) {
                    sum = sum + transactionTable.getAmount();

                }
                tvSumTags.setText(" " + sum + " ");
                realm.close();
                Toast.makeText(getApplicationContext(), addTagsToTransactionTable, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
