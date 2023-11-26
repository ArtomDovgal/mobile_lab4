package com.example.lab4.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab4.BaseViewModel;
import com.example.lab4.model.Book;
import com.example.lab4.model.Result;
import com.example.lab4.services.Callback;
import com.example.lab4.services.Cancellable;
import com.example.lab4.services.TolkienBooksService;

import java.util.Arrays;
import java.util.List;

public class MainViewModel extends BaseViewModel {

    private Result<List<Book>> booksResult = Result.empty();
    private MutableLiveData<ViewState> stateLiveData = new MutableLiveData<>();
    private Cancellable cancellable;

    {
       updateViewState(Result.empty());
    }

    public MainViewModel(TolkienBooksService tolkienBooksService) {
        super(tolkienBooksService);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(cancellable != null) cancellable.cancel();
    }

    public LiveData<ViewState> getViewState(){
        return stateLiveData;
    }

    public void getBook(String title){
        updateViewState(Result.loading());
        getTolkienBooksService().getBooks(title, new Callback<List<Book>>() {
            @Override
            public void onError(Throwable error) {
                if(booksResult.getStatus() != Result.Status.SUCCESS) {
                    updateViewState(Result.error(error));
                }
            }

            @Override
            public void onResult(List<Book> data) {
                updateViewState(Result.success(data));
            }
        });
    }


    private void updateViewState(Result<List<Book>> booksResult){
        ViewState state = new ViewState();
        state.enableSearchButton = booksResult.getStatus() != Result.Status.LOADING;
        state.isShowList = booksResult.getStatus() == Result.Status.SUCCESS;
        state.isShowError = booksResult.getStatus() == Result.Status.ERROR;
        state.isShowProgress = booksResult.getStatus() == Result.Status.LOADING;
        state.books = booksResult.getData();
        stateLiveData.setValue(state);
        this.booksResult = booksResult;
    }



    static class ViewState{
        private boolean enableSearchButton;
        private boolean isShowList;
        private boolean isShowEmptyHint;
        private boolean isShowError;
        private boolean isShowProgress;
        private List<Book> books;

        public boolean isEnableSearchButton() {
            return enableSearchButton;
        }

        public boolean isShowList() {
            return isShowList;
        }

        public boolean isShowEmptyHint() {
            return isShowEmptyHint;
        }

        public boolean isShowError() {
            return isShowError;
        }

        public boolean isShowProgress() {
            return isShowProgress;
        }

        public List<Book> getBooks() {
            return books;
        }

    }

}
