package com.nukedbit.core.observing;

public interface Observer<T> {
    void notify(T input);
}