package com.example.hp.pms_project.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.hp.pms_project.R;
import com.example.hp.pms_project.adapter.RealmTransactionsAdapter;
import com.example.hp.pms_project.adapter.TransactionsAdapter;
import com.example.hp.pms_project.model.transactionTable;
import com.example.hp.pms_project.realm.RealmController;
import com.example.hp.pms_project.utils.Prefs;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ShowAllTagsActivity extends AppCompatActivity {

//    private Button btnIncome;
//    private Button btnBudget;
//    private Button btnExpense;
   private Button btnAll;
    private TransactionsAdapter adapter;
    private RecyclerView recycler;
    private LayoutInflater inflater;
    private Realm realm;
    // private Button btnADayAgo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_tags);
        recycler = (RecyclerView) findViewById(R.id.recycler);
//        btnIncome = (Button) findViewById(R.id.btnIncome);
//        btnBudget = (Button) findViewById(R.id.btnBudget);
//        btnExpense = (Button) findViewById(R.id.btnExpense);
       // btnADayAgo = (Button) findViewById(R.id.btnADayAgo);
        btnAll = (Button) findViewById(R.id.btnAll);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        setRealmAdapter(RealmController.with(this).getTransactions());
        Log.d("ShowAllTagsActivity", "onClick: getTransactionsIncome " + RealmController.with(getApplication()).getTransactions());
//
//        btnIncome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RealmController.with(getApplication()).refresh();
//                setRealmAdapter(RealmController.with(getApplication()).getTransactionsIncome());
//                Log.d("ShowAllTagsActivity", "onClick: getTransactionsIncome " + RealmController.with(getApplication()).getTransactionsIncome());
//            }
//        });
//        btnBudget.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RealmController.with(getApplication()).refresh();
//                setRealmAdapter(RealmController.with(getApplication()).getTransactionsBudegt());
//                Log.d("ShowAllTagsActivity", "onClick: getTransactionsBudegt " + RealmController.with(getApplication()).getTransactionsBudegt());
//
//            }
//        });
//        btnExpense.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RealmController.with(getApplication()).refresh();
//                setRealmAdapter(RealmController.with(getApplication()).getTransactionsExpance());
//                Log.d("ShowAllTagsActivity", "onClick: getTransactionsExpance " + RealmController.with(getApplication()).getTransactionsExpance());
//
//
//            }
//        });
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmController.with(getApplication()).refresh();
                setRealmAdapter(RealmController.with(getApplication()).getTransactions());
            }
        });


        //Toast.makeText(this, "Press card item for edit, long press to remove item", Toast.LENGTH_LONG).show();

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
        adapter = new TransactionsAdapter(this);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.repots, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_aDayAgo) {
            RealmController.with(getApplication()).refresh();

            setRealmAdapter(RealmController.with(getApplication()).getTransactionsOneDay());
        }

        if (id == R.id.action_days15Ago) {
            RealmController.with(getApplication()).refresh();

            setRealmAdapter(RealmController.with(getApplication()).getTransactionsLast15Day());
        }

        if (id == R.id.action_lastWeek) {
            RealmController.with(getApplication()).refresh();

            setRealmAdapter(RealmController.with(getApplication()).getTransactionsLastWeek());
        }

        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }


}