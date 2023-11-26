package com.example.lab4.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.lab4.network.BookNetworkEntity;

@Entity(tableName = "books")
public class BookDBEntity {

    @PrimaryKey
    @NonNull
    private  String key;

    private  String title;
    private  String authors;
    private String rating;
    @ColumnInfo(name = "count_want_to_read")
    private Integer countWantToRead;

    @ColumnInfo(name = "number_of_pages_median")
    private Integer numberOfPagesMedian;
    @ColumnInfo(name = "first_publish_year")
    private Integer firstPublishYear;

    public BookDBEntity(String key, String title, String authors, String rating,
                        Integer countWantToRead, Integer numberOfPagesMedian, Integer firstPublishYear) {
        this.key = key;
        this.title = title;
        this.authors = authors;
        this.rating = rating;
        this.countWantToRead = countWantToRead;
        this.numberOfPagesMedian = numberOfPagesMedian;
        this.firstPublishYear = firstPublishYear;
    }

    public BookDBEntity(BookNetworkEntity bookNetworkEntity) {
        this.key = bookNetworkEntity.getKey();
        this.title = bookNetworkEntity.getTitle();
        this.authors = bookNetworkEntity.getAuthor();
        this.rating = bookNetworkEntity.getRating();
        this.countWantToRead = bookNetworkEntity.getCountWantToRead();
        this.numberOfPagesMedian = bookNetworkEntity.getNumberOfPagesMedian();
        this.firstPublishYear = bookNetworkEntity.getFirstPublishYear();
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return authors;
    }

    public String getRating() {
        return rating;
    }

    public Integer getCountWantToRead() {
        return countWantToRead;
    }

    public Integer getNumberOfPagesMedian() {
        return numberOfPagesMedian;
    }

    public Integer getFirstPublishYear() {
        return firstPublishYear;
    }

    public String getAuthors() {
        return authors;
    }
}
