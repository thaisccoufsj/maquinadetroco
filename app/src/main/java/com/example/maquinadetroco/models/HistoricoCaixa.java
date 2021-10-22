package com.example.maquinadetroco.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "HISTORICO_CAIXA")
public class HistoricoCaixa {
    @PrimaryKey(autoGenerate = true)
    private long id;

    public int getTotalMoeda5() {
        return totalMoeda5;
    }

    public void setTotalMoeda5(int totalMoeda5) {
        this.totalMoeda5 = totalMoeda5;
    }

    public int getTotalMoeda10() {
        return totalMoeda10;
    }

    public void setTotalMoeda10(int totalMoeda10) {
        this.totalMoeda10 = totalMoeda10;
    }

    public int getTotalMoeda25() {
        return totalMoeda25;
    }

    public void setTotalMoeda25(int totalMoeda25) {
        this.totalMoeda25 = totalMoeda25;
    }

    public int getTotalMoeda50() {
        return totalMoeda50;
    }

    public void setTotalMoeda50(int totalMoeda50) {
        this.totalMoeda50 = totalMoeda50;
    }

    public int getTotalMoeda1() {
        return totalMoeda1;
    }

    public void setTotalMoeda1(int totalMoeda1) {
        this.totalMoeda1 = totalMoeda1;
    }

    @ColumnInfo(name = "quantidadeMoeda5")
    private int quantidadeMoeda5 = 0;
    @ColumnInfo(name = "quantidadeMoeda10")
    private int quantidadeMoeda10 = 0;
    @ColumnInfo(name = "quantidadeMoeda25")
    private int quantidadeMoeda25 = 0;
    @ColumnInfo(name = "quantidadeMoeda50")
    private int quantidadeMoeda50 = 0;
    @ColumnInfo(name = "quantidadeMoeda1")
    private int quantidadeMoeda1;
    @ColumnInfo(name = "dataHora")
    private String dataHora;
    @ColumnInfo(name = "modo")
    private int modo;
    @ColumnInfo(name = "totalMoeda5")
    private int totalMoeda5;
    @ColumnInfo(name = "totalMoeda10")
    private int totalMoeda10;
    @ColumnInfo(name = "totalMoeda25")
    private int totalMoeda25;
    @ColumnInfo(name = "totalMoeda50")
    private int totalMoeda50;
    @ColumnInfo(name = "totalMoeda1")
    private int totalMoeda1;

    public HistoricoCaixa(){

    }

    public HistoricoCaixa(int quantidadeMoeda5, int quantidadeMoeda10, int quantidadeMoeda25,
                          int quantidadeMoeda50, int quantidadeMoeda1, int modo, int totalMoeda5,
                          int totalMoeda10, int totalMoeda25, int totalMoeda50, int totalMoeda1) {
        this.quantidadeMoeda5 = quantidadeMoeda5;
        this.quantidadeMoeda10 = quantidadeMoeda10;
        this.quantidadeMoeda25 = quantidadeMoeda25;
        this.quantidadeMoeda50 = quantidadeMoeda50;
        this.quantidadeMoeda1 = quantidadeMoeda1;
        this.modo = modo;
        this.totalMoeda5 = totalMoeda5;
        this.totalMoeda10 = totalMoeda10;
        this.totalMoeda25 = totalMoeda25;
        this.totalMoeda50 = totalMoeda50;
        this.totalMoeda1 = totalMoeda1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantidadeMoeda5() {
        return quantidadeMoeda5;
    }

    public void setQuantidadeMoeda5(int quantidadeMoeda5) {
        this.quantidadeMoeda5 = quantidadeMoeda5;
    }

    public int getQuantidadeMoeda10() {
        return quantidadeMoeda10;
    }

    public void setQuantidadeMoeda10(int quantidadeMoeda10) {
        this.quantidadeMoeda10 = quantidadeMoeda10;
    }

    public int getQuantidadeMoeda25() {
        return quantidadeMoeda25;
    }

    public void setQuantidadeMoeda25(int quantidadeMoeda25) {
        this.quantidadeMoeda25 = quantidadeMoeda25;
    }

    public int getQuantidadeMoeda50() {
        return quantidadeMoeda50;
    }

    public void setQuantidadeMoeda50(int quantidadeMoeda50) {
        this.quantidadeMoeda50 = quantidadeMoeda50;
    }

    public int getQuantidadeMoeda1() {
        return quantidadeMoeda1;
    }

    public void setQuantidadeMoeda1(int quantidadeMoeda1) {
        this.quantidadeMoeda1 = quantidadeMoeda1;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }
}