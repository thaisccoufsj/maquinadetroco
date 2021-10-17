package com.example.maquinadetroco.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.maquinadetroco.R;
import com.example.maquinadetroco.StateView;
import com.example.maquinadetroco.data.repository.AppDataBase;
import com.example.maquinadetroco.data.repository.CaixaRepo;
import com.example.maquinadetroco.data.repository.CaixaRepoImpl;
import com.example.maquinadetroco.databinding.ActivityCaixaBinding;
import com.example.maquinadetroco.dialogs.Dialogs;
import com.example.maquinadetroco.factorys.CaixaViewModelFactory;
import com.example.maquinadetroco.models.Caixa;
import com.example.maquinadetroco.utils.Constants;
import com.example.maquinadetroco.viewModels.CaixaViewModel;

public class CaixaActivity extends AppCompatActivity {

    private CaixaViewModel caixaViewModel;
    private ActivityCaixaBinding binding;
    private CaixaRepo repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCaixaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new CaixaRepoImpl(this.getApplication());
        caixaViewModel =  new ViewModelProvider(this, new CaixaViewModelFactory(repository)).get(CaixaViewModel.class);
        enableListeners();

        caixaViewModel.getCaixa().observe(this, caixa -> {
            binding.tvQuantidade5Centavos.setText(String.valueOf(caixa.getQuantidadeMoeda5()));
            binding.tvQuantidade10Centavos.setText(String.valueOf(caixa.getQuantidadeMoeda10()));
            binding.tvQuantidade25Centavos.setText(String.valueOf(caixa.getQuantidadeMoeda25()));
            binding.tvQuantidade50Centavos.setText(String.valueOf(caixa.getQuantidadeMoeda50()));
            binding.tvQuantidade1Real.setText(String.valueOf(caixa.getQuantidadeMoeda1()));
        });

        caixaViewModel.getStateView().observe(this, stateView -> {

            if(stateView.getCode() == Constants.STATE_VIEW_ERROR){
                Dialogs.getInstance(this).showMessage(stateView.getMessage());
            }

        });

    }

    private void enableListeners(){

        binding.btnAdicionarMoedas.setOnClickListener(view -> {

        });

        binding.btnRetirarMoedas.setOnClickListener(view -> {

        });

        binding.btnHistoricoCaixa.setOnClickListener(view -> {

        });

        binding.btnNovoTroco.setOnClickListener(view -> {

        });

    }

}