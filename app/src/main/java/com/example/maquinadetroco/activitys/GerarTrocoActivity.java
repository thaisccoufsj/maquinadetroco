package com.example.maquinadetroco.activitys;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import com.example.maquinadetroco.R;
import com.example.maquinadetroco.data.repository.CaixaRepoImpl;
import com.example.maquinadetroco.databinding.ActivityGerarTrocoBinding;
import com.example.maquinadetroco.dialogs.Dialogs;
import com.example.maquinadetroco.factorys.CaixaViewModelFactory;
import com.example.maquinadetroco.models.Caixa;
import com.example.maquinadetroco.utils.Constants;
import com.example.maquinadetroco.utils.Mascara;
import com.example.maquinadetroco.viewModels.GerarTrocoViewModel;
import java.text.NumberFormat;

public class GerarTrocoActivity extends AppCompatActivity {

    private ActivityGerarTrocoBinding binding;
    private GerarTrocoViewModel viewModel;
    private Caixa caixa;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityGerarTrocoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar myChildToolbar = binding.toolbar;
        setSupportActionBar(myChildToolbar);
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            caixa = (Caixa) bundle.getParcelable(getString(R.string.CAIXA));
        }

        if (caixa == null) {
            Dialogs.getInstance(this).showMessage("Dados do caixa não recebidos. Por favor tente novamente.",
                    (dialog, which) -> GerarTrocoActivity.this.finish());
        }

        viewModel = new ViewModelProvider(this, new CaixaViewModelFactory(new CaixaRepoImpl(this.getApplication()))).get(GerarTrocoViewModel.class);

        viewModel.getStateView().observe(this, stateView -> {

                switch (stateView.getCode()){

                    case Constants.STATE_VIEW_TROCO_CALCULADO:
                        habilitarGerarDesabilitarCalculo();
                        binding.tvResultadoTroco.setText(stateView.getMessage());
                        break;

                    case Constants.STATE_VIEW_TROCO_GERADO:
                        Dialogs.getInstance(this).showMessage("Troco gerado com sucesso!", (dialog, which) -> GerarTrocoActivity.this.finish());
                        break;

                    case Constants.STATE_VIEW_ERROR:
                        Dialogs.getInstance(this).showMessage(stateView.getMessage());
                        break;

                }

        });

        enableListeners();

    }

    private void enableListeners(){
        binding.tilValorCompra.getEditText().addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                habilitarCalculoDesabilitarGerar();
                viewModel.resetarValores();

                if(!binding.tilValorCompra.getEditText().getText().toString().isEmpty()){
                    binding.tilValorCompra.getEditText().removeTextChangedListener(this);
                    binding.tilValorCompra.getEditText().setText(Mascara.MascaraMonetario(s.toString()));
                    binding.tilValorCompra.getEditText().setSelection( binding.tilValorCompra.getEditText().getText().length());
                    binding.tilValorCompra.getEditText().addTextChangedListener(this);
                }

            }
        });
        binding.tilValorRecebido.getEditText().addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                habilitarCalculoDesabilitarGerar();
                viewModel.resetarValores();

                if(!binding.tilValorRecebido.getEditText().getText().toString().isEmpty()){
                    binding.tilValorRecebido.getEditText().removeTextChangedListener(this);
                    binding.tilValorRecebido.getEditText().setText(Mascara.MascaraMonetario(s.toString()));
                    binding.tilValorRecebido.getEditText().setSelection( binding.tilValorRecebido.getEditText().getText().length());
                    binding.tilValorRecebido.getEditText().addTextChangedListener(this);
                }

            }
        });

        binding.btnCalcularTroco.setOnClickListener(v -> {
            viewModel.calcularTroco(binding.tilValorCompra.getEditText().getText().toString(),
                    binding.tilValorRecebido.getEditText().getText().toString(),caixa);
        });

        binding.btnGerarTroco.setOnClickListener(v -> {
            viewModel.gerarTroco();
        });

    }

    private void habilitarCalculoDesabilitarGerar(){
        binding.btnCalcularTroco.setFocusable(true);
        binding.btnCalcularTroco.setEnabled(true);
        binding.btnGerarTroco.setFocusable(false);
        binding.btnGerarTroco.setEnabled(false);
        binding.tvResultadoTroco.setText("");
    }

    private void habilitarGerarDesabilitarCalculo(){
        binding.btnCalcularTroco.setFocusable(false);
        binding.btnCalcularTroco.setEnabled(false);
        binding.btnGerarTroco.setFocusable(true);
        binding.btnGerarTroco.setEnabled(true);
    }

}
