package com.example.android.datastorage;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.datastorage.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barcode = binding.barcodeEditText.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://world.openfoodfacts.org/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                OpenFoodService service = retrofit.create(OpenFoodService.class);
                Call<ProductResponseModel> call = service.product(barcode);
                call.enqueue(new Callback<ProductResponseModel>() {
                    @Override
                    public void onResponse(Call<ProductResponseModel> call, Response<ProductResponseModel> response) {
                        Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
                        ProductResponseModel productModel = response.body();
                        binding.showTextView.setText(productModel.product.getProduct_name());
                    }

                    @Override
                    public void onFailure(Call<ProductResponseModel> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });
            }
        });
    }
}
