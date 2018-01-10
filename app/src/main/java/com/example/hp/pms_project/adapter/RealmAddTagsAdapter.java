package com.example.hp.pms_project.adapter;

import android.content.Context;

import com.example.hp.pms_project.model.addTags;

import io.realm.RealmResults;

/**
 * Created by HP on 1/10/2018.
 */

public class RealmAddTagsAdapter extends RealmModelAdapter<addTags> {

    public RealmAddTagsAdapter(Context context, RealmResults<addTags> realmResults, boolean automaticUpdate) {

        super(context, realmResults, automaticUpdate);
    }
}