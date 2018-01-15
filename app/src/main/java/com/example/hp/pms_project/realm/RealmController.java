package com.example.hp.pms_project.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.example.hp.pms_project.model.addTags;
import com.example.hp.pms_project.model.transactionTable;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by HP on 12/26/2017.
 */


public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Book.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(transactionTable.class);
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<transactionTable> getTransactions() {

        return realm.where(transactionTable.class).findAll();
        //return realm.where(transactionTable.class).equalTo("type","Income").findAll();
    }

    //String tagName
    public RealmResults<transactionTable> getTransactionsTages(String tagName) {

        return realm.where(transactionTable.class).equalTo("tagName", tagName).findAll();
        //return realm.where(transactionTable.class).equalTo("type","Income").findAll();
    }

    public RealmResults<addTags> getTransactionsTags() {

        return realm.where(addTags.class).findAll();
        //return realm.where(transactionTable.class).equalTo("type","Income").findAll();
    }

    public RealmResults<transactionTable> getTransactionsIncome() {

        return realm.where(transactionTable.class).equalTo("type", "Income").findAll();
        //return realm.where(transactionTable.class).equalTo("type","Income").findAll();
    }

    public RealmResults<transactionTable> getTransactionsBudegt() {

        return realm.where(transactionTable.class).equalTo("type", "Budget").findAll();
        //return realm.where(transactionTable.class).equalTo("type","Income").findAll();
    }

    public RealmResults<transactionTable> getTransactionsExpance() {

        return realm.where(transactionTable.class).equalTo("type", "Expense").findAll();
        //return realm.where(transactionTable.class).equalTo("type","Income").findAll();
    }

    public RealmResults<transactionTable> getTransactionsOneDay() {
        long cuurentTime = System.currentTimeMillis();
        long ADayAgo = cuurentTime - 86400000;
        return realm.where(transactionTable.class).greaterThan("date", ADayAgo).findAll();
        //return realm.where(transactionTable.class).equalTo("type","Income").findAll();
    }

    public RealmResults<transactionTable> getTransactionsLast15Day() {
        long cuurentTime = System.currentTimeMillis();
        long Last15Days = cuurentTime - 1296000000;
        return realm.where(transactionTable.class).greaterThan("date", Last15Days).findAll();
        //return realm.where(transactionTable.class).equalTo("type","Income").findAll();
    }

    public RealmResults<transactionTable> getTransactionsLastWeek() {
        long cuurentTime = System.currentTimeMillis();
        long LastWeek = cuurentTime - 604800000;
        return realm.where(transactionTable.class).greaterThan("date", LastWeek).findAll();
        //return realm.where(transactionTable.class).equalTo("type","Income").findAll();
    }

    public RealmResults<transactionTable> getTransactionsLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date result = cal.getTime();

long month=result.getTime();

        return realm.where(transactionTable.class).greaterThan("date", month).findAll();
        //return realm.where(transactionTable.class).equalTo("type","Income").findAll();
    }

    public RealmResults<transactionTable> getSort() {
        RealmResults<transactionTable> results = realm.where(transactionTable.class).findAllSorted("id", false);
        return results;
    }

    public RealmResults<addTags> getSortTags() {
        RealmResults<addTags> results = realm.where(addTags.class).findAllSorted("id", false);
        return results;
    }


    //query a single item with the given id
    public transactionTable getBook(String id) {

        return realm.where(transactionTable.class).equalTo("id", id).findFirst();
    }

    //check if Book.class is empty
    public boolean hasBooks() {

        return !realm.allObjects(transactionTable.class).isEmpty();
    }

    //query example
    public RealmResults<transactionTable> queryedBooks() {

        return realm.where(transactionTable.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }
}
