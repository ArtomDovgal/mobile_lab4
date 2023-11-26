package com.example.lab4;

import androidx.lifecycle.ViewModel;

import com.example.lab4.services.TolkienBooksService;

public class BaseViewModel extends ViewModel {

    private TolkienBooksService tolkienBooksService;

    public BaseViewModel(TolkienBooksService tolkienBooksService) {
        super();
        this.tolkienBooksService = tolkienBooksService;
    }

    public TolkienBooksService getTolkienBooksService(){
        return this.tolkienBooksService;
    }
}
