package com.example.maquinadetroco.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CAIXA")
public class Caixa {

    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(name = "quantidadeMoeda5")
    public int quantidadeMoeda5 = 0;
    @ColumnInfo(name = "quantidadeMoeda10")
    public int quantidadeMoeda10 = 0;
    @ColumnInfo(name = "quantidadeMoeda25")
    public int quantidadeMoeda25 = 0;
    @ColumnInfo(name = "quantidadeMoeda50")
    public int quantidadeMoeda50 = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setQuantidadeMoeda5(int quantidadeMoeda5) {
        this.quantidadeMoeda5 = quantidadeMoeda5;
    }

    public void setQuantidadeMoeda10(int quantidadeMoeda10) {
        this.quantidadeMoeda10 = quantidadeMoeda10;
    }

    public void setQuantidadeMoeda25(int quantidadeMoeda25) {
        this.quantidadeMoeda25 = quantidadeMoeda25;
    }

    public void setQuantidadeMoeda50(int quantidadeMoeda50) {
        this.quantidadeMoeda50 = quantidadeMoeda50;
    }

    public void setQuantidadeMoeda1(int quantidadeMoeda1) {
        this.quantidadeMoeda1 = quantidadeMoeda1;
    }

    public int getQuantidadeMoeda5() {
        return quantidadeMoeda5;
    }

    public int getQuantidadeMoeda10() {
        return quantidadeMoeda10;
    }

    public int getQuantidadeMoeda25() {
        return quantidadeMoeda25;
    }

    public int getQuantidadeMoeda50() {
        return quantidadeMoeda50;
    }

    public int getQuantidadeMoeda1() {
        return quantidadeMoeda1;
    }

    @ColumnInfo(name = "quantidadeMoeda1")
    public int quantidadeMoeda1;

}