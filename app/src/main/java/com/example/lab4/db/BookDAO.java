package com.example.lab4.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.lab4.model.Book;

import java.util.List;

@Dao
public interface BookDAO {

    @Query("SELECT * FROM books WHERE title LIKE '%' || :title || '%' COLLATE NOCASE")
    List<BookDBEntity> getBooks(String title);
    @Query("SELECT * FROM books where key = :key")
    BookDBEntity getBookByKey(String key);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBookList(List<BookDBEntity> books);

    @Query("DELETE FROM books WHERE authors = :authors")
    void deleteBookByKey(String authors);

    default void updateBooks(String authors, List<BookDBEntity> books){
        deleteBookByKey(authors);
        insertBookList(books);
    }
}
