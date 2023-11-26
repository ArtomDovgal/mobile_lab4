package com.example.lab4.network;

import com.google.gson.annotations.SerializedName;

public class BookNetworkEntity {

    @SerializedName("key")
    private  String key;

    @SerializedName("title")
    private  String title;
    @SerializedName("author_name")
    private  String author;

    @SerializedName("ratings_average")
    private String rating;

    @SerializedName("want_to_read_count")
    private Integer countWantToRead;

    @SerializedName("number_of_pages_median")
    private Integer numberOfPagesMedian;

    @SerializedName("first_publish_year")
    private Integer firstPublishYear;


    public BookNetworkEntity(String key, String title, String author, String rating,
                             Integer countWantToRead, Integer numberOfPagesMedian, Integer firstPublishYear) {
        this.key = key;
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.countWantToRead = countWantToRead;
        this.numberOfPagesMedian = numberOfPagesMedian;
        this.firstPublishYear = firstPublishYear;
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
