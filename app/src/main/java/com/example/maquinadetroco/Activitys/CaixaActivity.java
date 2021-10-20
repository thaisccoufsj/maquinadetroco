package com.example.maquinadetroco.Activitys;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import com.example.maquinadetroco.R;
import com.example.maquinadetroco.data.repository.CaixaRepo;
import com.example.maquinadetroco.data.repository.CaixaRepoImpl;
import com.example.maquinadetroco.databinding.ActivityCaixaBinding;
import com.example.maquinadetroco.dialogs.Dialogs;
import com.example.maquinadetroco.factorys.CaixaViewModelFactory;
import com.example.maquinadetroco.models.Caixa;
import com.example.maquinadetroco.utils.Constants;
import com.example.maquinadetroco.utils.Funcoes;
import com.example.maquinadetroco.viewModels.CaixaViewModel;

public class CaixaActivity extends AppCompatActivity {

    private ActivityCaixaBinding binding;
    private CaixaViewModel caixaViewModel;
    private long id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCaixaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CaixaRepo repository = new CaixaRepoImpl(this.getApplication());
        caixaViewModel = new ViewModelProvider(this, new CaixaViewModelFactory(repository)).get(CaixaViewModel.class);
        enableListeners();

        caixaViewModel.getCaixa().observe(this, caixa -> {
            binding.tvQuantidade5Centavos.setText(String.valueOf(caixa.getQuantidadeMoeda5()));
            binding.tvQuantidade10Centavos.setText(String.valueOf(caixa.getQuantidadeMoeda10()));
            binding.tvQuantidade25Centavos.setText(String.valueOf(caixa.getQuantidadeMoeda25()));
            binding.tvQuantidade50Centavos.setText(String.valueOf(caixa.getQuantidadeMoeda50()));
            binding.tvQuantidade1Real.setText(String.valueOf(caixa.getQuantidadeMoeda1()));
            binding.tvTotal.setText(String.format(Funcoes.getLocale(),"R$ %10.2f",caixa.valorTotal()));
            id = caixa.getId();
        });

        caixaViewModel.getStateView().observe(this, stateView -> {
            if(stateView.getCode() == Constants.STATE_VIEW_ERROR){
                Dialogs.getInstance(this).showMessage(stateView.getMessage());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        caixaViewModel.carregarMoedas();
    }

    private void enableListeners(){

        binding.btnAdicionarMoedas.setOnClickListener(view -> {
            Intent intent = new Intent(this,AlterarCaixaActivity.class);
            intent.putExtra(getString(R.string.ACTIVITY_ALTERAR_CAIXA_MODO),Constants.ADICIONAR_MOEDAS);
            intent.putExtra(getString(R.string.ACTIVITY_ALTERAR_CAIXA_DADOS),obterCaixa());
            mStartForResult.launch(intent);
        });

        binding.btnRetirarMoedas.setOnClickListener(view -> {
            Intent intent = new Intent(this,AlterarCaixaActivity.class);
            intent.putExtra(getString(R.string.ACTIVITY_ALTERAR_CAIXA_MODO),Constants.RETIRAR_MOEDAS);
            intent.putExtra(getString(R.string.ACTIVITY_ALTERAR_CAIXA_DADOS),obterCaixa());
            mStartForResult.launch(intent);
        });

        binding.btnHistoricoCaixa.setOnClickListener(view -> {

        });

        binding.btnNovoTroco.setOnClickListener(view -> {

        });

    }

    private Caixa obterCaixa(){
        return new Caixa(id,Integer.parseInt(binding.tvQuantidade5Centavos.getText().toString()),
                Integer.parseInt(binding.tvQuantidade10Centavos.getText().toString()),
                Integer.parseInt(binding.tvQuantidade25Centavos.getText().toString()),
                Integer.parseInt(binding.tvQuantidade50Centavos.getText().toString()),
                Integer.parseInt(binding.tvQuantidade1Real.getText().toString()));

    }

    private final ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent intent = result.getData();
                switch (result.getResultCode()){
                    case Constants.ACTIVITY_ALTERAR_CAIXA_SUCESSO:

                        break;

                    case Constants.ACTIVITY_ALTERAR_CAIXA_ERRO:

                        break;

                }
            });

}