package com.example.maquinadetroco.data.repository;

import com.example.maquinadetroco.data.repository.RepositoryCallback;
import com.example.maquinadetroco.models.Caixa;
import com.example.maquinadetroco.models.HistoricoCaixa;

import java.util.List;

public interface CaixaRepo {

    void getCaixa(RepositoryCallback<List<Caixa>> callback);
    void updateCaixa(Caixa caixa,RepositoryCallback<Integer> callback);
    void insertCaixa(Caixa caixa,RepositoryCallback<Long> callback);
    void getHistorico(RepositoryCallback<List<HistoricoCaixa>> callback);
    void insertHistorico(HistoricoCaixa historicoCaixa,RepositoryCallback<Long> callback);
    void deleteHistorico(HistoricoCaixa historicoCaixa,RepositoryCallback<Void> callback);
}
