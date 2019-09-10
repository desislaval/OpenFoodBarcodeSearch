package com.example.android.datastorage.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("SELECT * FROM product WHERE code IN (:barcode) LIMIT 1")
    Product loadAllByBarcode(String barcode);

    @Insert
    void insertSingle(Product products);

    @Query("DELETE FROM product")
    void deleteAll();
}
