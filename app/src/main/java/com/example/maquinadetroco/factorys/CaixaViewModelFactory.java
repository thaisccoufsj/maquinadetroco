package com.example.maquinadetroco.factorys;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.maquinadetroco.data.repository.CaixaRepo;
import com.example.maquinadetroco.viewModels.AlterarCaixaViewModel;
import com.example.maquinadetroco.viewModels.CaixaViewModel;
import com.example.maquinadetroco.viewModels.GerarTrocoViewModel;
import com.example.maquinadetroco.viewModels.HistoricoCaixaViewModel;

public class CaixaViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final CaixaRepo repository;

    public  CaixaViewModelFactory(CaixaRepo repository){
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(modelClass.isAssignableFrom(CaixaViewModel.class)){
            return (T) new CaixaViewModel(repository);
        }else if(modelClass.isAssignableFrom(AlterarCaixaViewModel.class)){
            return (T) new AlterarCaixaViewModel(repository);
        }else if(modelClass.isAssignableFrom(HistoricoCaixaViewModel.class)){
            return (T) new HistoricoCaixaViewModel(repository);
        }else if(modelClass.isAssignableFrom(GerarTrocoViewModel.class)){
            return (T) new GerarTrocoViewModel(repository);
        }

        throw new IllegalArgumentException("Unable to construct ViewModel");
    }
}