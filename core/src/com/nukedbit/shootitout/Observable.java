package com.nukedbit.shootitout;

public interface Observable<T> {
    void subscribe(Observer<T> observer);
}