package com.example.hp.pms_project.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.pms_project.R;
import com.example.hp.pms_project.adapter.RealmTransactionsAdapter;
import com.example.hp.pms_project.adapter.TransactionsAdapter;
import com.example.hp.pms_project.model.transactionTable;
import com.example.hp.pms_project.realm.RealmController;
import com.example.hp.pms_project.utils.Prefs;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by HP on 1/2/2018.
 */

public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    long lnAmountExpance;
    private Realm realm;
    long lnAmountIncome;
    private TransactionsAdapter adapter;
    private RecyclerView recycler;
    private TextView tvTotalIncome;
    private TextView tvTotalExpance;
    private long sum;
    private long sumBudget;
    private long sumExpance;
    float sum1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        tvTotalIncome = (TextView) findViewById(R.id.tvTotalIncome);
        tvTotalExpance = (TextView) findViewById(R.id.tvTotalExpance);
        setSupportActionBar(toolbar);
        sum = 0;
        sumBudget = 0;
        sumExpance = 0;
        this.realm = RealmController.with(this).getRealm();
        setupRecycler();

        if (!Prefs.with(this).getPreLoad()) {
            setRealmData();
        }
        // refresh the realm instance
        RealmController.with(this).refresh();
        setRealmAdapter(RealmController.with(this).getTransactions());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
                startActivity(intent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRealmAdapter(RealmController.with(this).getTransactions());
        sum = 0;
        sumBudget = 0;
        sumExpance = 0;
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<transactionTable> query = realm.where(transactionTable.class);
        query.equalTo("type", "Income");
        RealmResults<transactionTable> manyIncome = query.findAll();
        for (com.example.hp.pms_project.model.transactionTable transactionTable : manyIncome) {
            lnAmountIncome = transactionTable.getAmount();
            sum = sum + lnAmountIncome;

        }
        tvTotalIncome.setText("+" + sum + "");

        RealmQuery<transactionTable> queryExpance = realm.where(transactionTable.class);
        queryExpance.equalTo("type", "Expense");
        RealmResults<transactionTable> manyExpance = queryExpance.findAll();
        for (com.example.hp.pms_project.model.transactionTable transactionTable : manyExpance) {
            lnAmountExpance = transactionTable.getAmount();
            sumExpance = sumExpance + lnAmountExpance;

        }
        tvTotalExpance.setText("-" + sumExpance + "");
        realm.close();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_all_tags) {
            // Handle the camera action

            Intent intent = new Intent(getApplicationContext(), ShowAllTagsActivity.class);
            startActivity(intent);

        }
        if (id == R.id.nav_graphs) {
            // Handle the camera action

            Intent intent = new Intent(getApplicationContext(), AllGraphsActivity.class);
            startActivity(intent);

        }

        if (id == R.id.nav_contact) {
            // Handle the camera action

            Intent intent = new Intent(getApplicationContext(), ContactUsAcivity.class);
            startActivity(intent);

        }

        if (id == R.id.nav_report) {
            // Handle the camera action

            Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
            startActivity(intent);

        }

        if (id == R.id.nav_logout) {
            // Handle the camera action

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);

        }
        if (id == R.id.nav_add_tag) {
            // Handle the camera action

            Intent intent = new Intent(getApplicationContext(), AddTagsActivity.class);
            startActivity(intent);

        }
        if (id == R.id.nav_select_tag_total) {
            // Handle the camera action

            Intent intent = new Intent(getApplicationContext(), SelectTagsTotalActivity.class);
            startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

