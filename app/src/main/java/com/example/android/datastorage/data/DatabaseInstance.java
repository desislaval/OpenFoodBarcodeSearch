package com.example.android.datastorage.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import java.util.List;

public class DatabaseInstance {
    private final ProductDatabase productDatabase;
    private static DatabaseInstance dbInstance;

    public static DatabaseInstance getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new DatabaseInstance(context);
        }
        return dbInstance;
    }

    private DatabaseInstance(Context context) {
        productDatabase = Room
                .databaseBuilder(context, ProductDatabase.class, "products.db")
                .build();
    }

    public void insertSingleAsync(final Product product) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                productDatabase.productDao().insertSingle(product);
                return null;
            }
        }.execute();
    }

    public void deleteAllAsync(final Product product) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                productDatabase.productDao().deleteAll();
                return null;
            }
        }.execute();
    }

    public void searchBarcodeAsync(final DatabaseListener<Product> callback, final String barcode) {
        new AsyncTask<Void, Void, Product>() {
            @Override
            protected Product doInBackground(Void... voids) {
                Product product = productDatabase.productDao().loadAllByBarcode(barcode);
                return product;
            }

            @Override
            protected void onPostExecute(Product product) {
                super.onPostExecute(product);
                callback.onDataReceived(product);
            }
        }.execute();
    }


    public interface DatabaseListener<T> {
        void onDataReceived(T data);
    }

}
