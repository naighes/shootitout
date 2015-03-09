package com.nukedbit.framework.observing;

public interface Observer<T> {
    void notify(T input);
}