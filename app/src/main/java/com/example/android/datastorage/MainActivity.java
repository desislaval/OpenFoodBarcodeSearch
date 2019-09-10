package com.example.android.datastorage;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.android.datastorage.data.DatabaseInstance;
import com.example.android.datastorage.data.Product;
import com.example.android.datastorage.data.ProductResponseModel;
import com.example.android.datastorage.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    DatabaseInstance dbi;
    Product productApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchDatabase();
            }
        });

    }

    private void searchDatabase() {
        final String barcode = binding.barcodeEditText.getText().toString();

        dbi = DatabaseInstance.getInstance(this);
        dbi.searchBarcodeAsync(new DatabaseInstance.DatabaseListener<Product>() {
            @Override
            public void onDataReceived(Product data) {
                if (data != null) {
                    binding.showSourceView.setText(getString(R.string.source, getString(R.string.database)));
                    binding.showBarcodeView.setText(getString(R.string.barcode, data.getCode()));
                    binding.showIngredientsView.setText(getString(R.string.ingredients_list, data.getIngerdientsList()));
                    binding.showProductView.setText(getString(R.string.product_name, data.getProductName()));
                } else
                    getProductInfoFromNet(barcode, dbi);
            }
        }, barcode);
    }


    private void getProductInfoFromNet(String barcode, final DatabaseInstance dbi) {
        productApi = new Product();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://world.openfoodfacts.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenFoodService service = retrofit.create(OpenFoodService.class);
        Call<ProductResponseModel> call = service.product(barcode);
        call.enqueue(new Callback<ProductResponseModel>() {
            @Override
            public void onResponse(Call<ProductResponseModel> call, Response<ProductResponseModel> response) {
                if (response.body() != null && response.isSuccessful()) {
                    ProductResponseModel productModel = response.body();
                    productApi.setCode(productModel.product.getCode());
                    productApi.setIngerdientsList(productModel.product.getIngredients_text());
                    productApi.setProductName(productModel.product.getProduct_name());
                    dbi.insertSingleAsync(productApi);

                    binding.showSourceView.setText(getString(R.string.source, getString(R.string.api)));
                    binding.showBarcodeView.setText(getString(R.string.barcode, productApi.getCode()));
                    binding.showIngredientsView.setText(getString(R.string.ingredients_list, productApi.getIngerdientsList()));
                    binding.showProductView.setText(getString(R.string.product_name, productApi.getProductName()));
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponseModel> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
