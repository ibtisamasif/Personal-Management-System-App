package com.example.hp.pms_project.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class AllGraphsActivity extends AppCompatActivity {

    private TextView tvTotalIncome;
    private TextView tvTotalBudget;
    private TextView tvTotalExpance;
    private Button btnIncExp;
    private Button btnBudExp;
    private Button btnIncBud;
    private PieChart pieChart;
    private long sum;
    private long sumBudget;
    private long sumExpance;
    float sum1 = 0;
    long lnAmountExpance;
    long lnAmountBudget;
    long lnAmountIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_graphs);
        tvTotalIncome = (TextView) findViewById(R.id.tvTotalIncome);
        tvTotalBudget = (TextView) findViewById(R.id.tvTotalBudget);
        tvTotalExpance = (TextView) findViewById(R.id.tvTotalExpance);
        btnIncExp = (Button) findViewById(R.id.btnIncExp);
        btnBudExp = (Button) findViewById(R.id.btnBudExp);
        btnIncBud = (Button) findViewById(R.id.btnIncBud);
        pieChart = (PieChart) findViewById(R.id.piechart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        sum = 0;
        sumBudget = 0;


        sumExpance = 0;
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<transactionTable> query = realm.where(transactionTable.class);
        query.equalTo("type", "Income");
        RealmResults<transactionTable> manyIncome = query.findAll();
        for (com.example.hp.pms_project.model.transactionTable transactionTable : manyIncome) {
            Log.d("NavigationDrawer", "onCreate: " + transactionTable.getAmount());

            lnAmountIncome = transactionTable.getAmount();
            sum = sum + lnAmountIncome;

        }
        tvTotalIncome.setText(sum + "");
        RealmQuery<transactionTable> querybudget = realm.where(transactionTable.class);
        querybudget.equalTo("type", "Budget");
        RealmResults<transactionTable> manyBudget = querybudget.findAll();
        for (com.example.hp.pms_project.model.transactionTable transactionTable : manyBudget) {
            Log.d("NavigationDrawer", "onCreate: " + transactionTable.getAmount());

            lnAmountBudget = transactionTable.getAmount();
            sumBudget = sumBudget + lnAmountBudget;

        }
        tvTotalBudget.setText(sumBudget + "");
        RealmQuery<transactionTable> queryExpance = realm.where(transactionTable.class);
        queryExpance.equalTo("type", "Expense");
        RealmResults<transactionTable> manyExpance = queryExpance.findAll();
        for (com.example.hp.pms_project.model.transactionTable transactionTable : manyExpance) {
            Log.d("NavigationDrawer", "onCreate: " + transactionTable.getAmount());

            lnAmountExpance = transactionTable.getAmount();
            sumExpance = sumExpance + lnAmountExpance;

        }
        tvTotalExpance.setText(sumExpance + "");
        realm.close();
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.99f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        btnBudExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBudExp();
            }
        });
        btnIncExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIncExp();

            }
        });
        btnIncBud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIncBug();
            }
        });

    }

    private void updateBudExp() {

        float f = Float.parseFloat(String.valueOf(sum));
        float f2 = Float.parseFloat(String.valueOf(sumExpance));
        float f1 = Float.parseFloat(String.valueOf(sumBudget));
        sum1 = 0;
        Toast.makeText(this, "" + sum + "  " + sumBudget + "  " + sumExpance + " f " + f, Toast.LENGTH_SHORT).show();
        ArrayList<PieEntry> yValues = new ArrayList<>();
        sum1 = f1 - f2;
        yValues.add(new PieEntry(f2, "Expense"));
        yValues.add(new PieEntry(sum1, "Budget Remaining"));
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        PieDataSet dataSet = new PieDataSet(yValues, "Status");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);
        pieChart.setData(data);
    }

    private void updateIncExp() {

        float f = Float.parseFloat(String.valueOf(sum));
        float f2 = Float.parseFloat(String.valueOf(sumExpance));
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
        data.setValueTextColor(Color.YELLOW);
        pieChart.setData(data);
    }

    private void updateIncBug() {

        float f = Float.parseFloat(String.valueOf(sum));
        float f1 = Float.parseFloat(String.valueOf(sumBudget));
        sum1 = 0;
        Toast.makeText(this, "" + sum + "  " + sumBudget + "  " + sumExpance + " f " + f, Toast.LENGTH_SHORT).show();
        ArrayList<PieEntry> yValues = new ArrayList<>();
        sum1 = f - f1;
        yValues.add(new PieEntry(f1, "Budget"));
        yValues.add(new PieEntry(sum1, "Income Remaining"));
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        PieDataSet dataSet = new PieDataSet(yValues, "Status");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);
        pieChart.setData(data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
