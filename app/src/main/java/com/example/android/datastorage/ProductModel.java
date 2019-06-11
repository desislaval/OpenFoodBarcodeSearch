package com.example.android.datastorage;

public class ProductModel {
    String code; //the code may have a leading zero
    String product_name;
    String ingredients_text;

    public String getCode() {
        return code;
    }

    public String getIngredients_text() {
        return ingredients_text;
    }

    public String getProduct_name() {
        return product_name;
    }
}
