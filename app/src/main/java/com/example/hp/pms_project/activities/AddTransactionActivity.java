//
//package com.example.hp.pms_project.activities;
//
//import android.app.DatePickerDialog;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//import com.example.hp.pms_project.R;
//import com.example.hp.pms_project.model.transactionTable;
//import com.example.hp.pms_project.utils.DateAndTimeUtils;
//import java.text.DateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import io.realm.Realm;
//
//public class AddTransactionActivity extends AppCompatActivity {
//
//
//    private TextView etDatePicker;
//    private EditText etDescription;
//    private EditText etAmount;
//    private EditText etTagName;
//    private EditText etStatus;
//    private EditText etMemo;
//    private Spinner spCategory;
//    private Button btnSaveTransaction;
//    Calendar dateTime = Calendar.getInstance();
//    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
//    public ArrayList<String> category;
//    private String showCategory;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_transaction);
//        etDatePicker = (TextView) findViewById(R.id.etDatePicker);
//        etDescription = (EditText) findViewById(R.id.etDescription);
//        etAmount = (EditText) findViewById(R.id.etAmount);
//        etTagName = (EditText) findViewById(R.id.etTagName);
//        etStatus = (EditText) findViewById(R.id.etStatus);
//        etMemo = (EditText) findViewById(R.id.etMemo);
//        spCategory = (Spinner) findViewById(R.id.spCategory);
//        btnSaveTransaction = (Button) findViewById(R.id.btnSaveTransaction);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }
//        category = new ArrayList<String>();
////        etDatePicker.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
//        // updateDate();
////            }
////        });
//        showAllCategoriesInSpinner();
//        // updateTextLabel();
//        final long time = Calendar.getInstance().getTimeInMillis();
//        etDatePicker.setText(DateAndTimeUtils.getDateTimeStringFromMiliseconds(time, "MMM dd,yyyy hh:mm:ss a"));
//
//        btnSaveTransaction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // ticket_time_in = Calendar.getInstance().getTimeInMillis();
////                String string_date = etDatePicker.getText().toString();
////                SimpleDateFormat f = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss a");
//
//                final long time_in = Calendar.getInstance().getTimeInMillis();
//
//                Realm realm = Realm.getDefaultInstance();
//                realm.executeTransaction(new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm bgRealm) {
//                        transactionTable transactionTable = bgRealm.createObject(com.example.hp.pms_project.model.transactionTable.class);
//                        transactionTable.setId(System.currentTimeMillis());
//                        transactionTable.setType(showCategory);
//                        transactionTable.setDescription(etDescription.getText().toString());
//                        transactionTable.setDate(time_in);
//                        transactionTable.setAmount(Long.parseLong(etAmount.getText().toString()));
//                        transactionTable.setTagId(System.currentTimeMillis());
//                        transactionTable.setTagName(etTagName.getText().toString());
//                        transactionTable.setStatus(etStatus.getText().toString() + "");
//                        transactionTable.setMemo(etMemo.getText().toString() + "");
//                    }
//
//                });
//                realm.close();
//                btnSaveTransaction.setVisibility(View.GONE);
//                finish();
//            }
//        });
//    }
//
//    private void updateDate() {
//        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
//    }
//
//    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
//        @Override
//        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//            dateTime.set(Calendar.YEAR, year);
//            dateTime.set(Calendar.MONTH, monthOfYear);
//            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//            updateTextLabel();
//        }
//    };
//
//    private void updateTextLabel() {
////        etDatePicker.setText(formatDateTime.format(dateTime.getTime()));
//    }
//
//    public void showAllCategoriesInSpinner() {
//        category.add("Income");
//        category.add("Expense");
//        category.add("Budget");
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spCategory.setAdapter(dataAdapter);
//        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
//                showCategory = String.valueOf(spCategory.getSelectedItem());
//                Toast.makeText(getApplicationContext(), showCategory, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//                // TODO Auto-generated method stub
//
//            }
//        });
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}


package com.example.hp.pms_project.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.pms_project.R;
import com.example.hp.pms_project.model.addTags;
import com.example.hp.pms_project.model.transactionTable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class AddTransactionActivity extends AppCompatActivity {

    private TextView etDatePicker;
    private EditText etDescription;
    private EditText etAmount;
    private EditText etTagName;
    private EditText etStatus;
    private EditText etMemo;
    private Spinner spCategory;
    private Spinner spTags;
    private Button btnSaveTransaction;
    Calendar dateTime = Calendar.getInstance();
    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    public ArrayList<String> category;
    public ArrayList<String> tagsCategory;
    private String showCategory;
    private String addTagsToTransactionTable;
    private TextView tvCreateTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        etDatePicker = (TextView) findViewById(R.id.etDatePicker);
        tvCreateTags = (TextView) findViewById(R.id.tvCreateTags);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etAmount = (EditText) findViewById(R.id.etAmount);
        // etTagName = (EditText) findViewById(R.id.etTagName);
        etStatus = (EditText) findViewById(R.id.etStatus);
        etMemo = (EditText) findViewById(R.id.etMemo);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        spTags = (Spinner) findViewById(R.id.spTags);
        btnSaveTransaction = (Button) findViewById(R.id.btnSaveTransaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        category = new ArrayList<String>();
        tagsCategory = new ArrayList<String>();
        etDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });
        tvCreateTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), AddTagsActivity.class);
                startActivity(intent);
            }
        });
        showAllCategoriesInSpinner();
        showAllTags();
        updateTextLabel();
        btnSaveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ticket_time_in = Calendar.getInstance().getTimeInMillis();
                String string_date = etDatePicker.getText().toString();
                SimpleDateFormat f = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss a");
                Date date = null;
                try {
                    date = f.parse(string_date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final long milliseconds = date.getTime();
                Log.d("milliseconds ", "onClick: " + milliseconds);
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {
                        transactionTable transactionTable = bgRealm.createObject(com.example.hp.pms_project.model.transactionTable.class);
                        transactionTable.setId(System.currentTimeMillis());
                        transactionTable.setType(showCategory);
                        transactionTable.setDescription(etDescription.getText().toString());
                        transactionTable.setDate(milliseconds);
                        transactionTable.setAmount(Long.parseLong(etAmount.getText().toString()));
                        transactionTable.setTagId(System.currentTimeMillis());
                        transactionTable.setTagName(addTagsToTransactionTable + "");
                        transactionTable.setStatus(etStatus.getText().toString() + "");
                        transactionTable.setMemo(etMemo.getText().toString() + "");
                    }

                });
                realm.close();
                btnSaveTransaction.setVisibility(View.GONE);
                finish();
            }
        });
    }

    private void updateDate() {
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTextLabel();
        }
    };

    private void updateTextLabel() {
        etDatePicker.setText(formatDateTime.format(dateTime.getTime()));
    }

    public void showAllCategoriesInSpinner() {
        category.add("Income");
        category.add("Budget");
        category.add("Expense");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(dataAdapter);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                showCategory = String.valueOf(spCategory.getSelectedItem());
                Toast.makeText(getApplicationContext(), showCategory, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    public void showAllTags() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<addTags> queryAllTags = realm.where(addTags.class);
//        queryAllTags.equalTo("type", "Expense");
        RealmResults<addTags> manyAddTags = queryAllTags.findAll();
        for (addTags addTags : manyAddTags) {
            //  Log.d("NavigationDrawer", "onCreate: " + addTags.getTagName());
            tagsCategory.add(addTags.getTagName());

        }

//        category.add("Income");
//        category.add("Budget");
//        category.add("Expense");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tagsCategory);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTags.setAdapter(dataAdapter);
        spTags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                addTagsToTransactionTable = String.valueOf(spTags.getSelectedItem());
                //Toast.makeText(getApplicationContext(), showCategory, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        super.onResume();
        //showAllTags();
    }
}
