package com.example.maquinadetroco.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "CAIXA")
public class Caixa implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "quantidadeMoeda5")
    private int quantidadeMoeda5 = 0;
    @ColumnInfo(name = "quantidadeMoeda10")
    private int quantidadeMoeda10 = 0;
    @ColumnInfo(name = "quantidadeMoeda25")
    private int quantidadeMoeda25 = 0;
    @ColumnInfo(name = "quantidadeMoeda50")
    private int quantidadeMoeda50 = 0;
    @ColumnInfo(name = "quantidadeMoeda1")
    private int quantidadeMoeda1 = 0;

    public double valorTotal(){
        return (quantidadeMoeda5 * 0.05) + (quantidadeMoeda10 * 0.10) + (quantidadeMoeda25 * 0.25)
                + (quantidadeMoeda50 * 0.5) + (quantidadeMoeda1);
    }

    public Caixa(){

    }

    @Ignore
    public Caixa(long id,int quantidadeMoeda5,int quantidadeMoeda10,int quantidadeMoeda25,
                 int quantidadeMoeda50,int quantidadeMoeda1){
        this.id = id;
        this.quantidadeMoeda5 = quantidadeMoeda5;
        this.quantidadeMoeda10 = quantidadeMoeda10;
        this.quantidadeMoeda25 = quantidadeMoeda25;
        this.quantidadeMoeda50 = quantidadeMoeda50;
        this.quantidadeMoeda1= quantidadeMoeda1;
    }

    @Ignore
    public Caixa(Parcel source){
        id = source.readLong();
        quantidadeMoeda5 = source.readInt();
        quantidadeMoeda10 = source.readInt();
        quantidadeMoeda25 = source.readInt();
        quantidadeMoeda50 = source.readInt();
        quantidadeMoeda1 = source.readInt();
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(quantidadeMoeda5);
        dest.writeInt(quantidadeMoeda10);
        dest.writeInt(quantidadeMoeda25);
        dest.writeInt(quantidadeMoeda50);
        dest.writeInt(quantidadeMoeda1);
    }

    public static final Creator<Caixa> CREATOR = new Creator<Caixa>() {
        @Override
        public Caixa createFromParcel(Parcel source) {
            return new Caixa(source);
        }

        @Override
        public Caixa[] newArray(int size) {
            return new Caixa[size];
        }
    };

}