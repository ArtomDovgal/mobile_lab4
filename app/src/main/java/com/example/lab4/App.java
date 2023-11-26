package com.example.lab4;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.lab4.db.AppDB;
import com.example.lab4.db.BookDAO;
import com.example.lab4.network.LibraryApi;
import com.example.lab4.services.TolkienBooksService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App  extends Application {
private static final String BASE_URL = "https://openlibrary.org/";
private ViewModelProvider.Factory viewModelFactory;

@Override
public void onCreate(){
    super.onCreate();

    OkHttpClient client = new OkHttpClient.Builder().
            addNetworkInterceptor(new HttpLoggingInterceptor().
                    setLevel(HttpLoggingInterceptor.Level.BODY)).build();

    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build();

    LibraryApi libraryApi = retrofit.create(LibraryApi.class);

    ExecutorService executorService = Executors.newCachedThreadPool();
    AppDB appDB = Room.databaseBuilder(this, AppDB.class,"database.db").allowMainThreadQueries().build();
    BookDAO bookDAO = appDB.getBookBAO();
    TolkienBooksService tolkienBooksService = new TolkienBooksService(libraryApi,bookDAO,executorService);
    viewModelFactory = new ViewModelFactory(tolkienBooksService);
}

public ViewModelProvider.Factory getViewModelFactory(){
return this.viewModelFactory;
}

}
