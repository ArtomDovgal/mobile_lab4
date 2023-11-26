package com.example.lab4.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.lab4.model.Book;

@Database(version = 1,entities = {BookDBEntity.class})
public abstract class AppDB extends RoomDatabase {
    public abstract BookDAO getBookBAO();
}
