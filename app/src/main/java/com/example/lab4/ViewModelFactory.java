package com.example.lab4;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab4.services.TolkienBooksService;

import java.lang.reflect.Constructor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private TolkienBooksService tolkienBooksService;

    public ViewModelFactory(TolkienBooksService tolkienBooksService) {
        this.tolkienBooksService = tolkienBooksService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            Constructor<T> constructor =  modelClass.getConstructor(TolkienBooksService.class);
            return constructor.newInstance(tolkienBooksService);
        }catch (ReflectiveOperationException e){
            Log.e(this.getClass().getSimpleName(),"Error",e);
            RuntimeException exception = new RuntimeException();
            exception.initCause(e);
            throw exception;
        }

    }

}
