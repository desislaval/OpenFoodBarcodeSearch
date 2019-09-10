package com.example.android.datastorage.data;

public class ProductModel {
    private String code; //the code may have a leading zero
    private String product_name;
    private String ingredients_text;

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
