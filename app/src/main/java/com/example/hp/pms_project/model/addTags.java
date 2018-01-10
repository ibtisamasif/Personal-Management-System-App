package com.example.hp.pms_project.model;

import io.realm.RealmObject;

/**
 * Created by HP on 1/10/2018.
 */

public class addTags extends RealmObject {

   private long id;
   private String tagName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
