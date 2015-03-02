package com.nukedbit.shootitout.observing;

public interface Observer<T> {
    void notify(T input);
}