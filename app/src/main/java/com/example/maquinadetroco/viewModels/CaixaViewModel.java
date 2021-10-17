package com.example.maquinadetroco.viewModels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.maquinadetroco.StateView;
import com.example.maquinadetroco.data.repository.CaixaRepo;
import com.example.maquinadetroco.data.repository.RepositoryCallback;
import com.example.maquinadetroco.models.Caixa;
import com.example.maquinadetroco.utils.Constants;
import java.util.List;

public class CaixaViewModel extends ViewModel {

    private final CaixaRepo repository;

    public CaixaViewModel(CaixaRepo repository) {
        this.repository = repository;
    }

    private final MutableLiveData<StateView> stateView = new MutableLiveData<>();
    public LiveData<StateView> getStateView() {
       return stateView;
    }

    private MutableLiveData<Caixa> caixa;
    public LiveData<Caixa> getCaixa() {

        if(caixa == null){
            caixa = new MutableLiveData<>(new Caixa());
            carregarMoedas();
        }

        return caixa;
    }


    private void carregarMoedas(){

        stateView.setValue(new StateView(Constants.STATE_VIEW_LOADING));

        new Thread(
                () -> repository.getCaixa(new RepositoryCallback<List<Caixa>>() {
                    @Override
                    public void onSucesso(List<Caixa> objeto) {
                        if(objeto != null){
                            if(objeto.isEmpty()){
                                inserirCaixa();
                            }else{
                                caixa.postValue(objeto.get(0));
                                stateView.postValue(new StateView(Constants.STATE_VIEW_DATA_LOADED));
                            }
                        }
                    }

                    @Override
                    public void onFalha(Throwable t) {
                        stateView.postValue(new StateView(Constants.STATE_VIEW_ERROR,"Erro ao carregar moedas."));
                    }
                })
        ).start();

    }

    private void inserirCaixa(){
        new Thread(() -> {
            repository.insertCaixa(new Caixa(), new RepositoryCallback<Long>() {
                @Override
                public void onSucesso(Long objeto) {
                    caixa.postValue(new Caixa());
                    stateView.postValue(new StateView(Constants.STATE_VIEW_DATA_LOADED));
                }

                @Override
                public void onFalha(Throwable t) {
                    stateView.postValue(new StateView(Constants.STATE_VIEW_ERROR,"Erro ao atualizar número de moedas."));
                }
            });
        }).start();
    }

}