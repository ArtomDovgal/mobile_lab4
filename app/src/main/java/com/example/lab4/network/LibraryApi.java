package com.example.lab4.network;

import com.example.lab4.model.Book;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LibraryApi {

//    @GET("/search.json")
//    Call<List<BookNetworkEntity>> getBooks(@Query("title")String author,@Query("limit")String limit);
    //Call<List<BookNetworkEntity>> getBooks(@Query("author")String author,@Query("limit")String limit);

    @GET("/search.json")
    Call<JsonObject> getBooks(@Query("title")String title,@Query("author") String author,
                              @Query("limit") Integer limit);

//    @GET("/{key}.json")
//    Call<JsonObject> getBookByKey(@Path("key")String key);
    @GET("/search.json")
    Call<JsonObject> getBookByKey(@Query("q")String key);

}
