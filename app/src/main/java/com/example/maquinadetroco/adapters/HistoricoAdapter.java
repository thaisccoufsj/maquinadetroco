package com.example.maquinadetroco.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.maquinadetroco.databinding.ItemHistoricoBinding;
import com.example.maquinadetroco.models.HistoricoCaixa;
import com.example.maquinadetroco.utils.Constants;
import com.example.maquinadetroco.utils.Funcoes;
import java.util.ArrayList;
import java.util.List;

public class HistoricoAdapter extends  RecyclerView.Adapter<HistoricoAdapter.ViewHolder>{

    private List<HistoricoCaixa> list = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoricoBinding binding = ItemHistoricoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoricoCaixa historicoCaixa = list.get(position);
        holder.bind(historicoCaixa);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private final ItemHistoricoBinding binding;

        public ViewHolder(ItemHistoricoBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(HistoricoCaixa historicoCaixa){

            int color = Color.BLACK;
            String sinal = "";
            String titulo = "";

            if(historicoCaixa.getModo() == Constants.ADICIONAR_MOEDAS){
                color = Color.GREEN;
                sinal = " + ";
                titulo = " (Adicionar)";
            }else if((historicoCaixa.getModo() == Constants.RETIRAR_MOEDAS) || (historicoCaixa.getModo() == Constants.GERAR_TROCO)){
                color = Color.RED;
                sinal = " - ";

                if(historicoCaixa.getModo() == Constants.RETIRAR_MOEDAS) titulo = " (Retirar)";
                else titulo = " (Troco)";

            }

            binding.dataHora.setText(historicoCaixa.getDataHora());
            binding.tvModo.setText(titulo);

            binding.tv5Centavos.setText(String.format(Funcoes.getLocale(),"%s%d",sinal,historicoCaixa.getQuantidadeMoeda5()));
            binding.tvTotalMoeda5.setText(String.valueOf(historicoCaixa.getTotalMoeda5()));
            binding.tv5Centavos.setTextColor(color);

            binding.tv10Centavos.setText(String.format(Funcoes.getLocale(),"%s%d",sinal,historicoCaixa.getQuantidadeMoeda10()));
            binding.tvTotalMoeda10.setText(String.valueOf(historicoCaixa.getTotalMoeda10()));
            binding.tv10Centavos.setTextColor(color);

            binding.tv25Centavos.setText(String.format(Funcoes.getLocale(),"%s%d",sinal,historicoCaixa.getQuantidadeMoeda25()));
            binding.tvTotalMoeda25.setText(String.valueOf(historicoCaixa.getTotalMoeda25()));
            binding.tv25Centavos.setTextColor(color);

            binding.tv50Centavos.setText(String.format(Funcoes.getLocale(),"%s%d",sinal,historicoCaixa.getQuantidadeMoeda50()));
            binding.tvTotalMoeda50.setText(String.valueOf(historicoCaixa.getTotalMoeda50()));
            binding.tv50Centavos.setTextColor(color);

            binding.tv1real.setText(String.format(Funcoes.getLocale(),"%s%d",sinal,historicoCaixa.getQuantidadeMoeda1()));
            binding.tvTotalMoeda1.setText(String.valueOf(historicoCaixa.getTotalMoeda1()));
            binding.tv1real.setTextColor(color);



        }
    }

    public void updateList(List<HistoricoCaixa> list){
        this.list = list;
        notifyDataSetChanged();
    }

}