package com.chaottic.taint.common;

public interface Registrant<T> {

    void register(String name, T t);
}
