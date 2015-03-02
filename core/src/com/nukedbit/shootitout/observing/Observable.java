package com.nukedbit.shootitout.observing;

public interface Observable<T> {
    void subscribe(Observer<T> observer);
}