package com.example.maquinadetroco.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maquinadetroco.R;
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

    public void salvar(int quantidadeMoeda5, int quantidadeMoeda10, int quantidadeMoeda25,
                                       int quantidadeMoeda50, int quantidadeMoeda1, Caixa caixa,int modo){

        stateView.setValue(new StateView(Constants.STATE_VIEW_LOADING));

        if((modo == Constants.RETIRAR_MOEDAS) && (!validarRetirarMoedas(quantidadeMoeda5,quantidadeMoeda10,
                quantidadeMoeda25,quantidadeMoeda50,quantidadeMoeda1,caixa))){
            stateView.postValue(new StateView(Constants.STATE_VIEW_ERROR,
                    "Não é possível retirar esses valores, pois o valor do caixa não pode ser negativo"));
            return;
        }

        adicionarHistorico(quantidadeMoeda5,quantidadeMoeda10,quantidadeMoeda25,quantidadeMoeda50,quantidadeMoeda1,caixa,modo);

    }

    private void adicionarHistorico(int quantidadeMoeda5, int quantidadeMoeda10, int quantidadeMoeda25,
                                    int quantidadeMoeda50, int quantidadeMoeda1, Caixa caixa,int modo){

        Caixa caixaAtualizado = new Caixa();
        caixaAtualizado.setId(caixa.getId());

        if(modo == Constants.ADICIONAR_MOEDAS){
            caixaAtualizado.setQuantidadeMoeda5(quantidadeMoeda5 + caixa.getQuantidadeMoeda5());
            caixaAtualizado.setQuantidadeMoeda10(quantidadeMoeda10 + caixa.getQuantidadeMoeda10());
            caixaAtualizado.setQuantidadeMoeda25(quantidadeMoeda25 + caixa.getQuantidadeMoeda25());
            caixaAtualizado.setQuantidadeMoeda50(quantidadeMoeda50 + caixa.getQuantidadeMoeda50());
            caixaAtualizado.setQuantidadeMoeda1(quantidadeMoeda1 + caixa.getQuantidadeMoeda1());
        }else if(modo == Constants.RETIRAR_MOEDAS){
            caixaAtualizado.setQuantidadeMoeda5(caixa.getQuantidadeMoeda5() - quantidadeMoeda5);
            caixaAtualizado.setQuantidadeMoeda10(caixa.getQuantidadeMoeda10() - quantidadeMoeda10);
            caixaAtualizado.setQuantidadeMoeda25(caixa.getQuantidadeMoeda25() - quantidadeMoeda25);
            caixaAtualizado.setQuantidadeMoeda50(caixa.getQuantidadeMoeda50() - quantidadeMoeda50);
            caixaAtualizado.setQuantidadeMoeda1(caixa.getQuantidadeMoeda1() - quantidadeMoeda1);
        }else{
            stateView.postValue(new StateView(Constants.STATE_VIEW_ERROR,"Modo de ação não identificado. Tente novamente."));
        }

        HistoricoCaixa historicoCaixa = new HistoricoCaixa(quantidadeMoeda5,quantidadeMoeda10,quantidadeMoeda25,
                quantidadeMoeda50,quantidadeMoeda1,modo,caixaAtualizado.getQuantidadeMoeda5(),caixaAtualizado.getQuantidadeMoeda10(),
                caixaAtualizado.getQuantidadeMoeda25(),caixaAtualizado.getQuantidadeMoeda50(),caixaAtualizado.getQuantidadeMoeda1());

        historicoCaixa.setDataHora(DataHora.DataHoraAtual());

        repository.insertHistorico(historicoCaixa, new RepositoryCallback<Long>() {
            @Override
            public void onSucesso(Long objeto) {
                historicoCaixa.setId(objeto);
                atualizarCaixa(caixaAtualizado,historicoCaixa,modo);
            }

            @Override
            public void onFalha(Throwable t) {
                stateView.postValue(new StateView(Constants.STATE_VIEW_ERROR,"Erro ao salvar valores. Tente novamente."));
            }
        });

    }
    
    private boolean validarRetirarMoedas(int quantidadeMoeda5, int quantidadeMoeda10, int quantidadeMoeda25,
                                         int quantidadeMoeda50, int quantidadeMoeda1, Caixa caixa){
        //valor retirado não pode deixar o caixa negativo
        return (quantidadeMoeda5 <= caixa.getQuantidadeMoeda5()) &&
                (quantidadeMoeda10 <= caixa.getQuantidadeMoeda10()) &&
                (quantidadeMoeda25 <= caixa.getQuantidadeMoeda25()) &&
                (quantidadeMoeda50 <= caixa.getQuantidadeMoeda50()) &&
                (quantidadeMoeda1 <= caixa.getQuantidadeMoeda1());
        
    }

    private void atualizarCaixa(Caixa caixa,HistoricoCaixa historicoCaixa,int modo){

        repository.updateCaixa(caixa, new RepositoryCallback<Integer>() {
            @Override
            public void onSucesso(Integer objeto) {
                 String msg;

                 if(modo == Constants.ADICIONAR_MOEDAS){
                     msg = "Moedas adicionadas com sucesso";
                 }else{
                     msg = "Moedas retiradas com sucesso";
                 }

                 stateView.postValue(new StateView(Constants.STATE_VIEW_DATA_SAVED,msg));
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