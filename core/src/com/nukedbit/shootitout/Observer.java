package com.nukedbit.shootitout;

public interface Observer<T> {
    void notify(T input);
}