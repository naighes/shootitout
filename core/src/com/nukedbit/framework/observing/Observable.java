package com.nukedbit.framework.observing;

public interface Observable<T> {
    void subscribe(Observer<T> observer);
}