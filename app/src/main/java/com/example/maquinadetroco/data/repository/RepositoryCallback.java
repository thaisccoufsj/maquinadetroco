package com.example.maquinadetroco.data.repository;

public interface RepositoryCallback<T> {
    void onSucesso(T objeto);
    void onFalha(Throwable t);
}