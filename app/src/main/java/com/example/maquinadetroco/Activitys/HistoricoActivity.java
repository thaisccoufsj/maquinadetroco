package com.example.maquinadetroco.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.maquinadetroco.R;
import com.example.maquinadetroco.adapters.HistoricoAdapter;
import com.example.maquinadetroco.data.repository.CaixaRepo;
import com.example.maquinadetroco.data.repository.CaixaRepoImpl;
import com.example.maquinadetroco.databinding.ActivityCaixaBinding;
import com.example.maquinadetroco.databinding.ActivityHistoricoBinding;
import com.example.maquinadetroco.factorys.CaixaViewModelFactory;
import com.example.maquinadetroco.models.HistoricoCaixa;
import com.example.maquinadetroco.viewModels.CaixaViewModel;
import com.example.maquinadetroco.viewModels.HistoricoCaixaViewModel;

import java.util.List;

public class HistoricoActivity extends AppCompatActivity {

    private ActivityHistoricoBinding binding;
    private HistoricoCaixaViewModel viewModel;
    private HistoricoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoricoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CaixaRepo repository = new CaixaRepoImpl(this.getApplication());
        viewModel = new ViewModelProvider(this, new CaixaViewModelFactory(repository)).get(HistoricoCaixaViewModel.class);

        adapter = new HistoricoAdapter();
        binding.rvHistorico.setAdapter(adapter);
        binding.rvHistorico.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getHistorico().observe(this, new Observer<List<HistoricoCaixa>>() {
            @Override
            public void onChanged(List<HistoricoCaixa> historicoCaixas) {
                adapter.updateList(historicoCaixas);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.histtorico_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.btnRecarregarHistorico){
            viewModel.carregar();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}