package com.example.hp.pms_project.model;

import io.realm.RealmObject;

/**
 * Created by HP on 12/22/2017.
 */

public class micomes extends RealmObject {

    private int id;
    private String type;
    private String desc;

//    private int amount;
//
//    private String eDate;
//
//    private int tagId;
//
//
//    private String tagName;
//
//    private String status;
//
//    private String createdAt;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

//    public int getAmount() {
//        return amount;
//    }
//
//    public void setAmount(int amount) {
//        this.amount = amount;
//    }
//
//    public String geteDate() {
//        return eDate;
//    }
//
//    public void seteDate(String eDate) {
//        this.eDate = eDate;
//    }
//
//    public int getTagId() {
//        return tagId;
//    }
//
//    public void setTagId(int tagId) {
//        this.tagId = tagId;
//    }
//
//    public String getTagName() {
//        return tagName;
//    }
//
//    public void setTagName(String tagName) {
//        this.tagName = tagName;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(String createdAt) {
//        this.createdAt = createdAt;
//    }


}
