package com.okayama.shop.data.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.okayama.shop.data.models.Product;

@Database(entities = { Product.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProductDao getProductDao();
}
