package com.nukedbit.core.observing;

public interface Observable<T> {
    void subscribe(Observer<T> observer);
}