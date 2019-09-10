package com.example.android.datastorage;

import com.example.android.datastorage.data.ProductResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OpenFoodService {
    @GET("api/v0/product/{barcode}.json")
    Call<ProductResponseModel> product(@Path("barcode") String barcode);
}
