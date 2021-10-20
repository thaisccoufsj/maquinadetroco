package com.example.maquinadetroco.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maquinadetroco.StateView;
import com.example.maquinadetroco.data.repository.CaixaRepo;
import com.example.maquinadetroco.data.repository.RepositoryCallback;
import com.example.maquinadetroco.models.Caixa;
import com.example.maquinadetroco.models.HistoricoCaixa;
import com.example.maquinadetroco.utils.Constants;
import com.example.maquinadetroco.utils.DataHora;

public class AlterarCaixaViewModel extends ViewModel {

    private final CaixaRepo repository;
    private final MutableLiveData<StateView> stateView = new MutableLiveData<>();
    public LiveData<StateView> getStateView() {
        return stateView;
    }

    public AlterarCaixaViewModel(CaixaRepo repository) {
        this.repository = repository;
    }

    public void processarAdicionarMoedas(int quantidadeMoeda5, int quantidadeMoeda10, int quantidadeMoeda25,
                                         int quantidadeMoeda50, int quantidadeMoeda1, Caixa caixa){
        stateView.setValue(new StateView(Constants.STATE_VIEW_LOADING));

        HistoricoCaixa historicoCaixa = new HistoricoCaixa(quantidadeMoeda5,quantidadeMoeda10,quantidadeMoeda25,
                quantidadeMoeda50,quantidadeMoeda1,Constants.ADICIONAR_MOEDAS);
        historicoCaixa.setDataHora(DataHora.DataHoraAtual());
        caixa.setQuantidadeMoeda5(quantidadeMoeda5);
        caixa.setQuantidadeMoeda10(quantidadeMoeda10);
        caixa.setQuantidadeMoeda25(quantidadeMoeda25);
        caixa.setQuantidadeMoeda50(quantidadeMoeda50);
        caixa.setQuantidadeMoeda1(quantidadeMoeda1);

        repository.insertHistorico(historicoCaixa, new RepositoryCallback<Long>() {
            @Override
            public void onSucesso(Long objeto) {
                adicionarMoedas(caixa,historicoCaixa);
            }

            @Override
            public void onFalha(Throwable t) {
                stateView.postValue(new StateView(Constants.STATE_VIEW_ERROR,"Erro ao salvar valores. Tente novamente."));
            }
        });

    }

    private void adicionarMoedas(Caixa caixa,HistoricoCaixa historicoCaixa){

        repository.updateCaixa(caixa, new RepositoryCallback<Integer>() {
            @Override
            public void onSucesso(Integer objeto) {
                 stateView.postValue(new StateView(Constants.STATE_VIEW_DATA_SAVED,"Moedas adicionadas com sucesso"));
            }

            @Override
            public void onFalha(Throwable t) {
                repository.deleteHistorico(historicoCaixa, new RepositoryCallback<Void>() {
                    @Override
                    public void onSucesso(Void objeto) {

                    }

                    @Override
                    public void onFalha(Throwable t) {

                    }
                });
                stateView.postValue(new StateView(Constants.STATE_VIEW_ERROR,"Erro ao salvar valores. Tente novamente."));
            }
        });

    }

}