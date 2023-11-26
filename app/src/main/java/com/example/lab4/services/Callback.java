package com.example.lab4.services;

public interface Callback<T> {

void onError(Throwable error);
void onResult(T data);
}
