package com.example.hp.pms_project.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.pms_project.R;
import com.example.hp.pms_project.model.transactionTable;
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
    long lnAmountBudget;
    long lnAmountIncome;
    private TextView tvTotalIncome;
    private TextView tvTotalBudget;
    private TextView tvTotalExpance;
    private PieChart pieChart;
    private long sum;
    private long sumBudget;
    private long sumExpance;
    float sum1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        tvTotalIncome = (TextView) findViewById(R.id.tvTotalIncome);
        tvTotalBudget = (TextView) findViewById(R.id.tvTotalBudget);
        tvTotalExpance = (TextView) findViewById(R.id.tvTotalExpance);
        pieChart = (PieChart) findViewById(R.id.piechart);
        setSupportActionBar(toolbar);
        sum = 0;
        sumBudget = 0;
        sumExpance = 0;

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        tvTotalIncome.setText(sum + "");
        RealmQuery<transactionTable> querybudget = realm.where(transactionTable.class);
        querybudget.equalTo("type", "Budget");
        RealmResults<transactionTable> manyBudget = querybudget.findAll();
        for (com.example.hp.pms_project.model.transactionTable transactionTable : manyBudget) {
            lnAmountBudget = transactionTable.getAmount();
            sumBudget = sumBudget + lnAmountBudget;

        }
        tvTotalBudget.setText(sumBudget + "");
        RealmQuery<transactionTable> queryExpance = realm.where(transactionTable.class);
        queryExpance.equalTo("type", "Expense");
        RealmResults<transactionTable> manyExpance = queryExpance.findAll();
        for (com.example.hp.pms_project.model.transactionTable transactionTable : manyExpance) {
            lnAmountExpance = transactionTable.getAmount();
            sumExpance = sumExpance + lnAmountExpance;

        }
        tvTotalExpance.setText(sumExpance + "");
        realm.close();
        float f = Float.parseFloat(String.valueOf(sum));
        float f2 = Float.parseFloat(String.valueOf(sumExpance));
        float f1 = Float.parseFloat(String.valueOf(sumBudget));
        sum1 = 0;
        Toast.makeText(this, "" + sum + "  " + sumBudget + "  " + sumExpance + " f " + f, Toast.LENGTH_SHORT).show();
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.99f);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        ArrayList<PieEntry> yValues = new ArrayList<>();
        sum1 = f1 - f2;
        yValues.add(new PieEntry(f2, "Expense"));
        yValues.add(new PieEntry(sum1, "Budget Remaining"));
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        PieDataSet dataSet = new PieDataSet(yValues, "Status");
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(2f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);
        pieChart.setData(data);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
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

