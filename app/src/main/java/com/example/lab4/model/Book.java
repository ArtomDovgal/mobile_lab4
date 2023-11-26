package com.example.lab4.model;

import com.example.lab4.db.BookDBEntity;
import com.example.lab4.network.BookNetworkEntity;
import com.google.gson.annotations.SerializedName;

public class Book {
    private  String key;
    private  String title;
    private  String author;
    private String rating;
    private Integer countWantToRead;

    private Integer numberOfPagesMedian;

    private Integer firstPublishYear;


    public Book(String key, String title, String author, String rating,
                Integer countWantToRead, Integer numberOfPagesMedian, Integer firstPublishYear) {
        this.key = key;
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.countWantToRead = countWantToRead;
        this.numberOfPagesMedian = numberOfPagesMedian;
        this.firstPublishYear = firstPublishYear;
    }

    public Book(BookNetworkEntity bookNetworkEntity){
        this.key = bookNetworkEntity.getKey();
        this.title = bookNetworkEntity.getTitle();
        this.author = bookNetworkEntity.getAuthor();
        this.rating = bookNetworkEntity.getRating();
        this.countWantToRead = bookNetworkEntity.getCountWantToRead();
        this.numberOfPagesMedian = bookNetworkEntity.getNumberOfPagesMedian();
        this.firstPublishYear = bookNetworkEntity.getFirstPublishYear();
    }

    public Book(BookDBEntity bookDBEntity){
        this.key = bookDBEntity.getKey();
        this.title = bookDBEntity.getTitle();
        this.author = bookDBEntity.getAuthor();
        this.rating = bookDBEntity.getRating();
        this.countWantToRead = bookDBEntity.getCountWantToRead();
        this.numberOfPagesMedian = bookDBEntity.getNumberOfPagesMedian();
        this.firstPublishYear = bookDBEntity.getFirstPublishYear();
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
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
}
