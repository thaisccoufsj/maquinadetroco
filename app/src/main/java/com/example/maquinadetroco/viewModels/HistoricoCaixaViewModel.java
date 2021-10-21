package com.example.maquinadetroco.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maquinadetroco.StateView;
import com.example.maquinadetroco.data.repository.CaixaRepo;
import com.example.maquinadetroco.data.repository.RepositoryCallback;
import com.example.maquinadetroco.models.HistoricoCaixa;
import com.example.maquinadetroco.utils.Constants;

import java.util.List;

public class HistoricoCaixaViewModel extends ViewModel {

    private final CaixaRepo repository;

    public HistoricoCaixaViewModel(CaixaRepo repository) {
        this.repository = repository;
    }

    private final MutableLiveData<StateView> stateView = new MutableLiveData<>();
    public LiveData<StateView> getStateView() {
        return stateView;
    }

    private final MutableLiveData<List<HistoricoCaixa>> listaHistorico = new MutableLiveData<>();
    public LiveData<List<HistoricoCaixa>> getHistorico() {
        carregar();
        return listaHistorico;
    }

    public void carregar(){

        stateView.postValue(new StateView(Constants.STATE_VIEW_LOADING));

        repository.getHistorico(new RepositoryCallback<List<HistoricoCaixa>>() {
            @Override
            public void onSucesso(List<HistoricoCaixa> objeto) {
                listaHistorico.postValue(objeto);
                stateView.postValue(new StateView(Constants.STATE_VIEW_DATA_LOADED));
            }

            @Override
            public void onFalha(Throwable t) {
                stateView.postValue(new StateView(Constants.STATE_VIEW_ERROR,"Não foi possível carregar o histórico, tente novamente."));
            }
        });

    }

}