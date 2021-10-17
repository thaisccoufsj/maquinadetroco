package com.example.maquinadetroco.factorys;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.maquinadetroco.data.repository.CaixaRepo;
import com.example.maquinadetroco.models.Caixa;
import com.example.maquinadetroco.viewModels.CaixaViewModel;

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
        }/*else if(modelClass.isAssignableFrom(DetailTodoViewModel::class.java)){
            return DetailTodoViewModel(repository,bundle) as T
        }*/

        throw new IllegalArgumentException("Unable to construct ViewModel");
    }
}