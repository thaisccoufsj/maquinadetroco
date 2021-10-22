package com.example.maquinadetroco.data.repository;

import android.app.Application;

import com.example.maquinadetroco.models.Caixa;
import com.example.maquinadetroco.models.HistoricoCaixa;

import java.util.List;

public class CaixaRepoImpl implements CaixaRepo {

    private final CaixaDAO caixaDAO;

    public CaixaRepoImpl(Application application){
        AppDataBase database = AppDataBase.getInstance(application);
        caixaDAO = database.caixaDAO();
    }


    @Override
    public void getCaixa(RepositoryCallback<List<Caixa>> callback) {

        new Thread(
                () -> {
                    try{
                        List<Caixa> list = caixaDAO.getCaixa();
                        callback.onSucesso(list);
                    }catch(Exception e){
                        callback.onFalha(e);
                    }
                }
        ).start();
    }

    @Override
    public void updateCaixa(Caixa caixa, RepositoryCallback<Integer> callback) {
        new Thread(() -> {
            int linhasAfetadas;
            try{
                linhasAfetadas = caixaDAO.updateCaixa(caixa);
            }catch (Exception e){
                linhasAfetadas = 0;
            }

            if(linhasAfetadas > 0) callback.onSucesso(linhasAfetadas);
            else callback.onFalha(new Exception("Falha ao atualizar."));

        }).start();
    }

    @Override
    public void insertCaixa(Caixa caixa, RepositoryCallback<Long> callback) {
        new Thread(() -> {
            long id;
            try{
                id = caixaDAO.insertCaixa(caixa);
            }catch (Exception e){
                id = 0;
            }

            if(id > 0) callback.onSucesso(id);
            else callback.onFalha(new Exception("Falha ao inserir."));

        }).start();
    }

    @Override
    public void getHistorico(RepositoryCallback<List<HistoricoCaixa>> callback) {
        new Thread(() -> {
            try{
                List<HistoricoCaixa> lista = caixaDAO.getHistorico();
                callback.onSucesso(lista);
            }catch (Exception e){
                callback.onFalha(e);
            }
        }).start();
    }

    @Override
    public void insertHistorico(HistoricoCaixa historicoCaixa, RepositoryCallback<Long> callback) {
        new Thread(() -> {
            long id;
            try{
                id = caixaDAO.insertHistorico(historicoCaixa);
            }catch (Exception e){
                id = 0;
            }

            if(id > 0) callback.onSucesso(id);
            else callback.onFalha(new Exception("Falha ao inserir."));

        }).start();
    }

    @Override
    public void deleteHistorico(HistoricoCaixa historicoCaixa, RepositoryCallback<Void> callback) {
        new Thread(() -> {
            try{
                caixaDAO.deleteHistorico(historicoCaixa.getId());
            }catch (Exception e){
                callback.onFalha(e);
            }
        }).start();
    }
}