package com.example.hp.pms_project.adapter;

import android.content.Context;

import com.example.hp.pms_project.model.transactionTable;

import io.realm.RealmResults;

/**
 * Created by HP on 1/2/2018.
 */
public class RealmTransactionsAdapter extends RealmModelAdapter<transactionTable> {

    public RealmTransactionsAdapter(Context context, RealmResults<transactionTable> realmResults, boolean automaticUpdate) {

        super(context, realmResults, automaticUpdate);
    }
}