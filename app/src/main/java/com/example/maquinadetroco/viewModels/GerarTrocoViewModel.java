package com.example.maquinadetroco.viewModels;

import android.text.Editable;
import android.text.TextWatcher;

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
import com.example.maquinadetroco.utils.Funcoes;
import com.example.maquinadetroco.utils.Mascara;

public class GerarTrocoViewModel extends ViewModel {

    private final CaixaRepo repository;
    private final MutableLiveData<StateView> stateView = new MutableLiveData<>();
    public LiveData<StateView> getStateView() {
        return stateView;
    }
    private int moedasTroco5 = 0;
    private int moedasTroco10 = 0;
    private int moedasTroco25 = 0;
    private int moedasTroco50 = 0;
    private int moedasTroco1 = 0;
    private Caixa caixa;

    public GerarTrocoViewModel(CaixaRepo repository){
        this.repository = repository;
    }

    public void calcularTroco(String valorCompraString, String valorRecebidoString, Caixa caixa){

        stateView.postValue(new StateView(Constants.STATE_VIEW_LOADING));
        this.caixa = caixa;

        if(valorCompraString.isEmpty() || valorRecebidoString.isEmpty()){
            stateView.postValue(new StateView(Constants.STATE_VIEW_ERROR,"Digite todos valores para continuar."));
            return;
        }

        float valorCompra = Float.parseFloat(Mascara.MascaraMonetario(valorCompraString).replace(",","."));
        float valorRecebido = Float.parseFloat(Mascara.MascaraMonetario(valorRecebidoString).replace(",","."));

        if((valorCompra == 0) || (valorRecebido == 0)){
            stateView.postValue(new StateView(Constants.STATE_VIEW_ERROR,"Digite todos valores maiores do que zero para continuar."));
            return;
        }

        if(valorCompra > valorRecebido){
            stateView.postValue(new StateView(Constants.STATE_VIEW_ERROR,"O valor recebido não pode ser menor do que o valor da compra."));
            return;
        }

        if(valorCompra == valorRecebido){
            stateView.postValue(new StateView(Constants.STATE_VIEW_ERROR,"O valor da compra é igual ao valor recebido. Nenhum troco é necessário."));
            return;
        }

        float saldo = valorRecebido - valorCompra;

        if(saldo > caixa.valorTotal()){
            stateView.postValue(new StateView(Constants.STATE_VIEW_ERROR,"O valor em caixa não é suficiente para este troco."));
            return;
        }

        while(saldo > 0){

            if(saldo >= 1){
                moedasTroco1++;
                saldo--;
                continue;
            }

            if(saldo >= 0.5){
                moedasTroco50++;
                saldo -= 0.5;
                continue;
            }

            if((saldo >= 0.25) ){
                moedasTroco25++;
                saldo -= 0.25;
                continue;
            }

            if((saldo >= 0.10)){
                moedasTroco10++;
                saldo -= 0.10;
                continue;
            }

            if((moedasTroco5 <= 0) && (saldo > 0)){
                cancelarOperacao();
                return;
            }

            if((saldo > 0)){
                moedasTroco5++;
                saldo -= 0.05;
            }

        }

        stateView.postValue(new StateView(Constants.STATE_VIEW_TROCO_CALCULADO,String.format(Funcoes.getLocale(),
                "Para este troco é necessário : \n %d moedas de 5 centavos \n %d moedas de 10 centavos \n %d moedas de 25 centavos \n %d moedas de 50 centavos \n %d moedas de 1 real",
                moedasTroco5,moedasTroco10,moedasTroco25,moedasTroco50,moedasTroco1)));


    }

    private void cancelarOperacao(){
        resetarValores();
        stateView.postValue(new StateView(Constants.STATE_VIEW_ERROR,"O caixa não possui as moedas necessárias para este troco."));
    }

    public void gerarTroco(){

        Caixa caixaNova = new Caixa();
        caixaNova.setId(caixa.getId());
        caixaNova.setQuantidadeMoeda5(caixa.getQuantidadeMoeda5() - moedasTroco5);
        caixaNova.setQuantidadeMoeda10(caixa.getQuantidadeMoeda10() - moedasTroco10);
        caixaNova.setQuantidadeMoeda25(caixa.getQuantidadeMoeda25() - moedasTroco25);
        caixaNova.setQuantidadeMoeda50(caixa.getQuantidadeMoeda50() - moedasTroco50);
        caixaNova.setQuantidadeMoeda1(caixa.getQuantidadeMoeda1() - moedasTroco1);

        HistoricoCaixa historicoCaixa = new HistoricoCaixa(moedasTroco5,moedasTroco10,moedasTroco25,
                moedasTroco50,moedasTroco1,Constants.GERAR_TROCO,caixaNova.getQuantidadeMoeda5(),caixaNova.getQuantidadeMoeda10(),
                caixaNova.getQuantidadeMoeda25(),caixaNova.getQuantidadeMoeda50(),caixaNova.getQuantidadeMoeda1());

        historicoCaixa.setDataHora(DataHora.DataHoraAtual());

        repository.insertHistorico(historicoCaixa, new RepositoryCallback<Long>() {
            @Override
            public void onSucesso(Long objeto) {
                historicoCaixa.setId(objeto);
                atualizarCaixa(caixaNova,historicoCaixa);
            }

            @Override
            public void onFalha(Throwable t) {
                stateView.postValue(new StateView(Constants.STATE_VIEW_ERROR,"Erro ao salvar valores. Tente novamente."));
            }
        });

    }

    private void atualizarCaixa(Caixa caixa,HistoricoCaixa historicoCaixa){
        repository.updateCaixa(caixa, new RepositoryCallback<Integer>() {
            @Override
            public void onSucesso(Integer objeto) {
                stateView.postValue(new StateView(Constants.STATE_VIEW_TROCO_GERADO,"O troco foi processado com sucesso."));
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

    public void resetarValores(){
        moedasTroco5 = 0;
        moedasTroco10 = 0;
        moedasTroco25 = 0;
        moedasTroco50 = 0;
        moedasTroco1 = 0;
        caixa = null;
    }


}
