package com.example.lab4.services;


import android.util.Log;

import com.annimon.stream.Stream;
import com.example.lab4.db.BookDAO;
import com.example.lab4.db.BookDBEntity;
import com.example.lab4.model.Book;
import com.example.lab4.network.BookNetworkEntity;
import com.example.lab4.network.LibraryApi;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Response;

public class TolkienBooksService {
    private static  final String author = "tolkien";
    private static final Integer limit = 3;
    private ExecutorService executorService;
    private BookDAO bookDAO;
    private LibraryApi libraryApi;

    public TolkienBooksService(LibraryApi libraryApi,BookDAO bookDAO,ExecutorService executorService){
        this.libraryApi = libraryApi;
        this.bookDAO = bookDAO;
        this.executorService = executorService;
    }
    
    public Cancellable getBooks(String title, Callback<List<Book>> callback) {
        Call<JsonObject> call = libraryApi.getBooks(title, author, limit);
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject responseObject = response.body();
                    JsonArray docsArray = responseObject.getAsJsonArray("docs");

                    List<BookNetworkEntity> newBooks = convertToBookNetworkEntity(docsArray);
                    List<BookDBEntity> newBooksDB = convertFromBookNetToDB(newBooks);
                    bookDAO.updateBooks(author,newBooksDB);
                    callback.onResult(convertFromBookNetToBook(newBooks));
                } else {
                    List<BookDBEntity> booksDB = bookDAO.getBooks(title);
                    List<Book> books = convertFromBookBDtoBook(booksDB);
                    if(books.isEmpty()){
                        callback.onError(new RuntimeException("RESPONSE ERROR"));
                    }else{
                        callback.onResult(books);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                List<BookDBEntity> booksDB = bookDAO.getBooks(title);
                List<Book> books = convertFromBookBDtoBook(booksDB);
                if(books.isEmpty()){
                    callback.onError(new RuntimeException("NETWORK ERROR"));
                }else{
                    callback.onResult(books);
                }

            }
        });
        return call::cancel;
    }
public Cancellable getBookByKey(String key,Callback<Book> callback){
        Future<?> future = executorService.submit(()->{
            try{
                BookDBEntity bookDB = bookDAO.getBookByKey(key);
                Book book = new Book(bookDB);
                callback.onResult(book);
            }catch (Exception e){
                callback.onError(e);
            }
        });

    return new FutureCancellable(future);
}


private List<Book> convertFromBookNetToBook(List<BookNetworkEntity> entities) {
    return Stream.of(entities).map(Book::new).toList();
}
private List<BookDBEntity> convertFromBookNetToDB(List<BookNetworkEntity> entities){
    return Stream.of(entities).map(BookDBEntity::new).toList();
}
private List<Book> convertFromBookBDtoBook(List<BookDBEntity> entities){
    return Stream.of(entities).map(Book::new).toList();
    }

    private List<BookNetworkEntity> convertToBookNetworkEntity(JsonArray docsArray) {
        List<BookNetworkEntity> books = new ArrayList<>();
        for (JsonElement element : docsArray) {
            JsonObject docObject = element.getAsJsonObject();
            String key = docObject.get("key").getAsString();
            String title = docObject.get("title").getAsString();
            String author = getAuthorsAsString(docObject);
            Integer year = docObject.has("first_publish_year") ?
                    docObject.getAsJsonPrimitive("first_publish_year").getAsInt() : 0;
            String rating = docObject.has("ratings_average") ?
                    docObject.getAsJsonPrimitive("ratings_average").getAsString() : "---";
            Integer countWantToRead = docObject.has("want_to_read_count") ?
                    docObject.getAsJsonPrimitive("want_to_read_count").getAsInt() : 0;
            Integer numberOfPagesMedian = docObject.has("number_of_pages_median") ?
                    docObject.getAsJsonPrimitive("number_of_pages_median").getAsInt() : 0;
            books.add(new BookNetworkEntity(key, title, author,rating,countWantToRead,numberOfPagesMedian,year));
        }
        return books;
    }
    private String getAuthorsAsString(JsonObject docObject) {
        JsonArray authorArray = docObject.getAsJsonArray("author_name");

        if (authorArray != null && !authorArray.isJsonNull() && authorArray.size() > 0) {
            List<String> authors = new ArrayList<>();
            for (JsonElement authorElement : authorArray) {
                authors.add(authorElement.getAsString());
            }
            return String.join(", ", authors);
        } else {
            return "";
        }
    }
    static class FutureCancellable implements Cancellable {
        private Future<?> future;

        public FutureCancellable(Future<?> future) {
            this.future = future;
        }

        @Override
        public void cancel() {
            future.cancel(true);
        }
    }
}
