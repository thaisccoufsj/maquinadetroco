package com.example.maquinadetroco.Activitys;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.example.maquinadetroco.databinding.ActivityAlterarCaixaBinding;

public class AlterarCaixaActivity extends AppCompatActivity {

    private ActivityAlterarCaixaBinding binding;

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

    }
}