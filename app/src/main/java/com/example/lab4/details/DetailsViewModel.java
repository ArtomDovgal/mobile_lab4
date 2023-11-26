package com.example.lab4.details;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab4.BaseViewModel;
import com.example.lab4.model.Book;
import com.example.lab4.model.Result;
import com.example.lab4.services.Callback;
import com.example.lab4.services.Cancellable;
import com.example.lab4.services.TolkienBooksService;

public class DetailsViewModel extends BaseViewModel {

    private MutableLiveData<Result<Book>> bookLiveData = new MutableLiveData<>();

    private Cancellable cancelable;
    private TextView title;
    private TextView author;

    public DetailsViewModel(TolkienBooksService tolkienBooksService) {
        super(tolkienBooksService);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(cancelable != null) cancelable.cancel();
    }

    public void loadBookByKey(String key){
        bookLiveData.setValue(Result.loading());
        cancelable = getTolkienBooksService().getBookByKey(key, new Callback<Book>() {
            @Override
            public void onResult(Book data) {
                bookLiveData.postValue(Result.success(data));
            }

            @Override
            public void onError(Throwable error) {
                bookLiveData.postValue(Result.error(error));
            }

        });
    }


    public LiveData<Result<Book>> getResults(){
        return bookLiveData;
    }
}

