package com.example.maquinadetroco.data.repository;

import com.example.maquinadetroco.models.Caixa;

import java.util.List;

public interface CaixaRepo {

    void getCaixa(RepositoryCallback<List<Caixa>> callback);
    void updateCaixa(Caixa caixa,RepositoryCallback<Integer> callback);
    void insertCaixa(Caixa caixa,RepositoryCallback<Long> callback);

}
