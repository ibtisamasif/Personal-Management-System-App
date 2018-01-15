package com.example.hp.pms_project.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.pms_project.R;
import com.example.hp.pms_project.adapter.IncExpBudAdapter;
import com.example.hp.pms_project.adapter.RealmTransactionsAdapter;
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
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static android.graphics.Color.*;

public class ReportActivity extends AppCompatActivity {

    private TextView tvTotalIncome;
    private TextView tvTotalBudget;
    private TextView tvTotalExpance;
    private Button btnLastWeek;
    private Button btnLast15Days;
    private PieChart pieChart;
    private IncExpBudAdapter adapter;
    private RecyclerView recycler;
    private long sum;
    private long sumBudget;
    private long sumExpance;
    long lnAmountExpance;
    long lnAmountBudget;
    float sum1 = 0;
    long lnAmountIncome;
    private Realm realm;
    boolean check = false;
    private Button btnMonth;
    private Spinner spType;
    public ArrayList<String> category;
    private String showCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        tvTotalIncome = (TextView) findViewById(R.id.tvTotalIncome);
        tvTotalBudget = (TextView) findViewById(R.id.tvTotalBudget);
        tvTotalExpance = (TextView) findViewById(R.id.tvTotalExpance);
        btnLastWeek = (Button) findViewById(R.id.btnLastWeek);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        btnLast15Days = (Button) findViewById(R.id.btnLast15Days);
        btnMonth = (Button) findViewById(R.id.btnMonth);
        pieChart = (PieChart) findViewById(R.id.piechart);
        spType = (Spinner) findViewById(R.id.spType);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        category = new ArrayList<String>();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        showAllCategoriesInSpinner();

        this.realm = RealmController.with(this).getRealm();
        setupRecycler();

        if (!Prefs.with(this).getPreLoad()) {
            setRealmData();
        }
        // refresh the realm instance
        RealmController.with(this).refresh();
        btnLastWeek.setBackgroundColor(btnLastWeek.getContext().getResources().getColor(R.color.red));
        setRealmAdapter(RealmController.with(getApplication()).getTransactionsLastWeek());
        update();
        btnLastWeek.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                btnLastWeek.setBackgroundColor(btnLastWeek.getContext().getResources().getColor(R.color.red));
                btnLast15Days.setBackgroundColor(btnLast15Days.getContext().getResources().getColor(R.color.gray));
                btnMonth.setBackgroundColor(btnMonth.getContext().getResources().getColor(R.color.gray));

                setRealmAdapter(RealmController.with(getApplication()).getTransactionsLastWeek());
                update();
            }
        });

        btnLast15Days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLastWeek.setBackgroundColor(btnLastWeek.getContext().getResources().getColor(R.color.gray));
                btnLast15Days.setBackgroundColor(btnLast15Days.getContext().getResources().getColor(R.color.red));
                btnMonth.setBackgroundColor(btnMonth.getContext().getResources().getColor(R.color.gray));
                setRealmAdapter(RealmController.with(getApplication()).getTransactionsLast15Day());
                update15DDaysAgo();
            }
        });
        btnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnLastWeek.setBackgroundColor(btnLastWeek.getContext().getResources().getColor(R.color.gray));
                btnLast15Days.setBackgroundColor(btnLast15Days.getContext().getResources().getColor(R.color.gray));
                btnMonth.setBackgroundColor(btnMonth.getContext().getResources().getColor(R.color.red));
                setRealmAdapter(RealmController.with(getApplication()).getTransactionsLastMonth());
                updateLastMonth();
            }
        });
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.99f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(WHITE);
        pieChart.setTransparentCircleRadius(61f);
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
        for (transactionTable t : transactionsArrayList) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(t);
            realm.commitTransaction();
        }
        Prefs.with(this).setPreLoad(true);
    }


    private void updateLastMonth() {
        sum = 0;
        sumBudget = 0;
        sumExpance = 0;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date result = cal.getTime();
        long month = result.getTime();
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<transactionTable> query = realm.where(transactionTable.class);
        query.equalTo("type", "Income");
        query.greaterThan("date", month).findAll();
        RealmResults<transactionTable> manyIncome = query.findAll();
        for (com.example.hp.pms_project.model.transactionTable transactionTable : manyIncome) {
            Log.d("NavigationDrawer", "onCreate: " + transactionTable.getAmount());
            lnAmountIncome = transactionTable.getAmount();
            sum = sum + lnAmountIncome;
        }
        tvTotalIncome.setText(sum + "");
        RealmQuery<transactionTable> querybudget = realm.where(transactionTable.class);
        querybudget.equalTo("type", "Budget");
        querybudget.greaterThan("date", month).findAll();
        RealmResults<transactionTable> manyBudget = querybudget.findAll();
        for (com.example.hp.pms_project.model.transactionTable transactionTable : manyBudget) {
            Log.d("NavigationDrawer", "onCreate: " + transactionTable.getAmount());
            lnAmountBudget = transactionTable.getAmount();
            sumBudget = sumBudget + lnAmountBudget;
        }
        tvTotalBudget.setText(sumBudget + "");
        RealmQuery<transactionTable> queryExpance = realm.where(transactionTable.class);
        queryExpance.equalTo("type", "Expense");
        queryExpance.greaterThan("date", month).findAll();
        RealmResults<transactionTable> manyExpance = queryExpance.findAll();
        for (com.example.hp.pms_project.model.transactionTable transactionTable : manyExpance) {
            Log.d("NavigationDrawer", "onCreate: " + transactionTable.getAmount());
            lnAmountExpance = transactionTable.getAmount();
            sumExpance = sumExpance + lnAmountExpance;
        }
        tvTotalExpance.setText(sumExpance + "");
        float f = Float.parseFloat(String.valueOf(sum));
        float f2 = Float.parseFloat(String.valueOf(sumExpance));
        float f1 = Float.parseFloat(String.valueOf(sumBudget));
        sum1 = 0;
        Toast.makeText(this, "" + sum + "  " + sumBudget + "  " + sumExpance + " f " + f, Toast.LENGTH_SHORT).show();
        ArrayList<PieEntry> yValues = new ArrayList<>();
        sum1 = f - f2;
        yValues.add(new PieEntry(f2, "Expense"));
        yValues.add(new PieEntry(sum1, "Income Remaining"));
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        PieDataSet dataSet = new PieDataSet(yValues, "Status");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(YELLOW);
        pieChart.setData(data);
    }

    public void update() {
        sum = 0;
        sumBudget = 0;
        sumExpance = 0;
        long cuurentTime = System.currentTimeMillis();
        long lastWeek = cuurentTime - 604800000;
        long cuurentTime15 = System.currentTimeMillis();
        long last15DAys = cuurentTime15 - 1296000000;
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<transactionTable> query = realm.where(transactionTable.class);
        query.equalTo("type", "Income");
        query.greaterThan("date", lastWeek).findAll();
        RealmResults<transactionTable> manyIncome = query.findAll();
        for (com.example.hp.pms_project.model.transactionTable transactionTable : manyIncome) {
            Log.d("NavigationDrawer", "onCreate: " + transactionTable.getAmount());
            lnAmountIncome = transactionTable.getAmount();
            sum = sum + lnAmountIncome;
        }
        tvTotalIncome.setText(sum + "");
        RealmQuery<transactionTable> querybudget = realm.where(transactionTable.class);
        querybudget.equalTo("type", "Budget");
        querybudget.greaterThan("date", lastWeek).findAll();
        RealmResults<transactionTable> manyBudget = querybudget.findAll();
        for (com.example.hp.pms_project.model.transactionTable transactionTable : manyBudget) {
            Log.d("NavigationDrawer", "onCreate: " + transactionTable.getAmount());
            lnAmountBudget = transactionTable.getAmount();
            sumBudget = sumBudget + lnAmountBudget;
        }
        tvTotalBudget.setText(sumBudget + "");
        RealmQuery<transactionTable> queryExpance = realm.where(transactionTable.class);
        queryExpance.equalTo("type", "Expense");
        queryExpance.greaterThan("date", lastWeek).findAll();
        RealmResults<transactionTable> manyExpance = queryExpance.findAll();
        for (com.example.hp.pms_project.model.transactionTable transactionTable : manyExpance) {
            Log.d("NavigationDrawer", "onCreate: " + transactionTable.getAmount());
            lnAmountExpance = transactionTable.getAmount();
            sumExpance = sumExpance + lnAmountExpance;

        }
        tvTotalExpance.setText(sumExpance + "");

        float f = Float.parseFloat(String.valueOf(sum));
        float f2 = Float.parseFloat(String.valueOf(sumExpance));
        float f1 = Float.parseFloat(String.valueOf(sumBudget));
        sum1 = 0;
        Toast.makeText(this, "" + sum + "  " + sumBudget + "  " + sumExpance + " f " + f, Toast.LENGTH_SHORT).show();
        ArrayList<PieEntry> yValues = new ArrayList<>();
        sum1 = f - f2;
        yValues.add(new PieEntry(f2, "Expense"));
        yValues.add(new PieEntry(sum1, "Income Remaining"));
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        PieDataSet dataSet = new PieDataSet(yValues, "Status");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(YELLOW);
        pieChart.setData(data);
    }

    private void update15DDaysAgo() {
        sum = 0;
        sumBudget = 0;
        sumExpance = 0;
        long cuurentTime15 = System.currentTimeMillis();
        long last15DAys = cuurentTime15 - 1296000000;
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<transactionTable> query = realm.where(transactionTable.class);
        query.equalTo("type", "Income");
        query.greaterThan("date", last15DAys).findAll();
        RealmResults<transactionTable> manyIncome = query.findAll();
        for (com.example.hp.pms_project.model.transactionTable transactionTable : manyIncome) {
            Log.d("NavigationDrawer", "onCreate: " + transactionTable.getAmount());
            lnAmountIncome = transactionTable.getAmount();
            sum = sum + lnAmountIncome;
        }
        tvTotalIncome.setText(sum + "");
        RealmQuery<transactionTable> querybudget = realm.where(transactionTable.class);
        querybudget.equalTo("type", "Budget");
        querybudget.greaterThan("date", last15DAys).findAll();
        RealmResults<transactionTable> manyBudget = querybudget.findAll();
        for (com.example.hp.pms_project.model.transactionTable transactionTable : manyBudget) {
            Log.d("NavigationDrawer", "onCreate: " + transactionTable.getAmount());
            lnAmountBudget = transactionTable.getAmount();
            sumBudget = sumBudget + lnAmountBudget;
        }
        tvTotalBudget.setText(sumBudget + "");
        RealmQuery<transactionTable> queryExpance = realm.where(transactionTable.class);
        queryExpance.equalTo("type", "Expense");
        queryExpance.greaterThan("date", last15DAys).findAll();
        RealmResults<transactionTable> manyExpance = queryExpance.findAll();
        for (com.example.hp.pms_project.model.transactionTable transactionTable : manyExpance) {
            Log.d("NavigationDrawer", "onCreate: " + transactionTable.getAmount());
            lnAmountExpance = transactionTable.getAmount();
            sumExpance = sumExpance + lnAmountExpance;
        }
        tvTotalExpance.setText(sumExpance + "");
        float f = Float.parseFloat(String.valueOf(sum));
        float f2 = Float.parseFloat(String.valueOf(sumExpance));
        float f1 = Float.parseFloat(String.valueOf(sumBudget));
        sum1 = 0;
        Toast.makeText(this, "" + sum + "  " + sumBudget + "  " + sumExpance + " f " + f, Toast.LENGTH_SHORT).show();
        ArrayList<PieEntry> yValues = new ArrayList<>();
        sum1 = f - f2;
        yValues.add(new PieEntry(f2, "Expense"));
        yValues.add(new PieEntry(sum1, "Income Remaining"));
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        PieDataSet dataSet = new PieDataSet(yValues, "Status");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(YELLOW);
        pieChart.setData(data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void showAllCategoriesInSpinner() {
        category.add("Income");
        category.add("Budget");
        category.add("Expense");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(dataAdapter);
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                showCategory = String.valueOf(spType.getSelectedItem());
               // Toast.makeText(getApplicationContext(), spType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

}
