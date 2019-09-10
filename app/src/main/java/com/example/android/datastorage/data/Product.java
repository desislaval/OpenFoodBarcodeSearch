package com.example.android.datastorage.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "code")
    public String code;

    @ColumnInfo(name = "product_name")
    public String productName;

    @ColumnInfo(name = "ingredients_list")
    public String ingerdientsList;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getIngerdientsList() {
        return ingerdientsList;
    }

    public void setIngerdientsList(String ingerdientsList) {
        this.ingerdientsList = ingerdientsList;
    }

    @NonNull
    @Override
    public String toString() {
        return getProductName() + " \n" + getCode() + " \n" + getIngerdientsList();
    }
}
