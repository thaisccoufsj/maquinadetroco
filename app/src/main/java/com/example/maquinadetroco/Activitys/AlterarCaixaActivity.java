package com.example.maquinadetroco.Activitys;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.maquinadetroco.R;
import com.example.maquinadetroco.StateView;
import com.example.maquinadetroco.data.repository.CaixaRepo;
import com.example.maquinadetroco.data.repository.CaixaRepoImpl;
import com.example.maquinadetroco.databinding.ActivityAlterarCaixaBinding;
import com.example.maquinadetroco.dialogs.Dialogs;
import com.example.maquinadetroco.factorys.CaixaViewModelFactory;
import com.example.maquinadetroco.models.Caixa;
import com.example.maquinadetroco.utils.Constants;
import com.example.maquinadetroco.utils.Funcoes;
import com.example.maquinadetroco.viewModels.AlterarCaixaViewModel;
import java.util.Objects;

public class AlterarCaixaActivity extends AppCompatActivity {

    private ActivityAlterarCaixaBinding binding;
    private AlterarCaixaViewModel viewModel;
    private Caixa caixa;
    private int modo = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlterarCaixaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar myChildToolbar = binding.toolbar;
        setSupportActionBar(myChildToolbar);
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        CaixaRepo repository = new CaixaRepoImpl(this.getApplication());
        viewModel = new ViewModelProvider(this, new CaixaViewModelFactory(repository)).get(AlterarCaixaViewModel.class);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            caixa = (Caixa) bundle.getParcelable(getString(R.string.ACTIVITY_ALTERAR_CAIXA_DADOS));
            modo = bundle.getInt(getString(R.string.ACTIVITY_ALTERAR_CAIXA_MODO),-1);
        }

        if ((caixa != null) && (modo != -1)) {
            Objects.requireNonNull(binding.tilTotalMoeda5.getEditText()).setText(String.valueOf(caixa.getQuantidadeMoeda5()));
            Objects.requireNonNull(binding.tilTotalMoeda10.getEditText()).setText(String.valueOf(caixa.getQuantidadeMoeda10()));
            Objects.requireNonNull(binding.tilTotalMoeda25.getEditText()).setText(String.valueOf(caixa.getQuantidadeMoeda25()));
            Objects.requireNonNull(binding.tilTotalMoeda50.getEditText()).setText(String.valueOf(caixa.getQuantidadeMoeda50()));
            Objects.requireNonNull(binding.tilTotalMoeda1.getEditText()).setText(String.valueOf(caixa.getQuantidadeMoeda1()));
            binding.tvValorTotal.setText(String.format(Funcoes.getLocale(),"R$ %10.2f",caixa.valorTotal()));
        }else{
            Dialogs.getInstance(this).showMessage("Dados do caixa não recebidos. Por favor tente novamente.",
                    (dialog, which) -> AlterarCaixaActivity.this.finish());
        }

        viewModel.getStateView().observe(this, new Observer<StateView>() {
            @Override
            public void onChanged(StateView stateView) {

                switch (stateView.getCode()){

                    case Constants.STATE_VIEW_DATA_SAVED:
                        String mensagem = "";
                        if(modo == Constants.ADICIONAR_MOEDAS){
                            mensagem = "As moedas foram adicionadas com sucesso";
                        }else{
                            mensagem = "As moedas foram retiradas com sucesso";
                        }

                        Dialogs.getInstance(AlterarCaixaActivity.this).showMessage(mensagem,(dialog,which) -> {
                            AlterarCaixaActivity.this.finish();
                        });

                        break;

                    case Constants.STATE_VIEW_ERROR:
                        Dialogs.getInstance(AlterarCaixaActivity.this).showMessage(stateView.getMessage());
                        break;

                }

            }
        });
        enableListeners();

    }

    private void enableListeners(){

        binding.btnSalvar.setOnClickListener(view -> {
             if(modo == Constants.ADICIONAR_MOEDAS){
                  viewModel.processarAdicionarMoedas(Integer.parseInt(binding.tilTotalMoeda5.getEditText().getText().toString()),
                          Integer.parseInt(binding.tilTotalMoeda10.getEditText().getText().toString()),
                          Integer.parseInt(binding.tilTotalMoeda25.getEditText().getText().toString()),
                          Integer.parseInt(binding.tilTotalMoeda50.getEditText().getText().toString()),
                          Integer.parseInt(binding.tilTotalMoeda1.getEditText().getText().toString()),caixa);
             }
        });

        //Eventos para atualizar em temop real as quantidades e o valor total
        EditText etMoeda5  = binding.tilQuantidadeMoeda5.getEditText();
        EditText etMoeda10 = binding.tilQuantidadeMoeda10.getEditText();
        EditText etMoeda25 = binding.tilQuantidadeMoeda25.getEditText();
        EditText etMoeda50 = binding.tilQuantidadeMoeda50.getEditText();
        EditText etMoeda1  = binding.tilQuantidadeMoeda1.getEditText();

        if (etMoeda5 != null) {
            etMoeda5.addTextChangedListener(textWatcher);
        }

        if (etMoeda10 != null) {
            etMoeda10.addTextChangedListener(textWatcher);
        }

        if (etMoeda25 != null) {
            etMoeda25.addTextChangedListener(textWatcher);
        }

        if (etMoeda50 != null) {
            etMoeda50.addTextChangedListener(textWatcher);
        }

        if (etMoeda1 != null) {
            etMoeda1.addTextChangedListener(textWatcher);
        }


    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(modo == Constants.ADICIONAR_MOEDAS){
                atualizarValoresParaAdicaoDeMoedas();
            }else if(modo == Constants.RETIRAR_MOEDAS){
                atualizarValoresParaRemocaoDeMoedas();
            }
        }
    };

    private void atualizarValoresParaAdicaoDeMoedas(){

         int totalMoeda5 = caixa.getQuantidadeMoeda5();
         if(!binding.tilQuantidadeMoeda5.getEditText().getText().toString().isEmpty()){
             totalMoeda5 += Integer.parseInt(binding.tilQuantidadeMoeda5.getEditText().getText().toString());
         }

         int totalMoeda10 = caixa.getQuantidadeMoeda10();
         if(!binding.tilQuantidadeMoeda10.getEditText().getText().toString().isEmpty()){
             totalMoeda10 += Integer.parseInt(binding.tilQuantidadeMoeda10.getEditText().getText().toString());
         }

         int totalMoeda25 = caixa.getQuantidadeMoeda25();
         if(!binding.tilQuantidadeMoeda25.getEditText().getText().toString().isEmpty()){
             totalMoeda25 += Integer.parseInt(binding.tilQuantidadeMoeda25.getEditText().getText().toString());
         }

         int totalMoeda50 = caixa.getQuantidadeMoeda50();
        if(!binding.tilQuantidadeMoeda50.getEditText().getText().toString().isEmpty()){
            totalMoeda50 += Integer.parseInt(binding.tilQuantidadeMoeda50.getEditText().getText().toString());
        }

         int totalMoeda1 = caixa.getQuantidadeMoeda1();
        if(!binding.tilQuantidadeMoeda1.getEditText().getText().toString().isEmpty()){
            totalMoeda1 += Integer.parseInt(binding.tilQuantidadeMoeda1.getEditText().getText().toString());
        }

         atualizarQuantidadeMoedasEValorTotal(totalMoeda5,totalMoeda10,totalMoeda25,totalMoeda50,totalMoeda1);

    }

    private void atualizarValoresParaRemocaoDeMoedas(){

        int totalMoeda5 = caixa.getQuantidadeMoeda5();
        if(!binding.tilQuantidadeMoeda5.getEditText().getText().toString().isEmpty()){
            totalMoeda5 -= Integer.parseInt(binding.tilQuantidadeMoeda5.getEditText().getText().toString());
        }

        int totalMoeda10 = caixa.getQuantidadeMoeda10();
        if(!binding.tilQuantidadeMoeda10.getEditText().getText().toString().isEmpty()){
            totalMoeda10 -= Integer.parseInt(binding.tilQuantidadeMoeda10.getEditText().getText().toString());
        }

        int totalMoeda25 = caixa.getQuantidadeMoeda25();
        if(!binding.tilQuantidadeMoeda25.getEditText().getText().toString().isEmpty()){
            totalMoeda25 -= Integer.parseInt(binding.tilQuantidadeMoeda25.getEditText().getText().toString());
        }

        int totalMoeda50 = caixa.getQuantidadeMoeda50();
        if(!binding.tilQuantidadeMoeda50.getEditText().getText().toString().isEmpty()){
            totalMoeda50 -= Integer.parseInt(binding.tilQuantidadeMoeda50.getEditText().getText().toString());
        }

        int totalMoeda1 = caixa.getQuantidadeMoeda1();
        if(!binding.tilQuantidadeMoeda1.getEditText().getText().toString().isEmpty()){
            totalMoeda1 -= Integer.parseInt(binding.tilQuantidadeMoeda1.getEditText().getText().toString());
        }

       atualizarQuantidadeMoedasEValorTotal(totalMoeda5,totalMoeda10,totalMoeda25,totalMoeda50,totalMoeda1);

    }

    private void atualizarQuantidadeMoedasEValorTotal(int totalMoeda5, int totalMoeda10,
                                                      int totalMoeda25, int totalMoeda50, int totalMoeda1){
        Caixa c = new Caixa(caixa.getId(),totalMoeda5,totalMoeda10,totalMoeda25,totalMoeda50,totalMoeda1);
        binding.tilTotalMoeda5.getEditText().setText(String.valueOf(totalMoeda5));
        binding.tilTotalMoeda10.getEditText().setText(String.valueOf(totalMoeda10));
        binding.tilTotalMoeda25.getEditText().setText(String.valueOf(totalMoeda25));
        binding.tilTotalMoeda50.getEditText().setText(String.valueOf(totalMoeda50));
        binding.tilTotalMoeda1.getEditText().setText(String.valueOf(totalMoeda1));
        binding.tvValorTotal.setText(String.format(Funcoes.getLocale(),"R$ %10.2f",c.valorTotal()));
    }

}