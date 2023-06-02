package com.example.followyourshifts.Interfaces;

public interface DataCallBack<T> {
    void onDataReceived(T data);
    void onDataError(String errorMessage);
}
