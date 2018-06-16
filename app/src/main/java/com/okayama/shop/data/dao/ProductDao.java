package com.okayama.shop.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.okayama.shop.data.models.Product;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ProductDao {

    @Insert(onConflict = REPLACE)
    void insert(Product product);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM product")
    List<Product> getAllProduct();
}
