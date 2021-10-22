package com.example.maquinadetroco.utils;

public final class Mascara {

    public static String MascaraMonetario(String s){
        String valor = s.replaceAll("[^\\d.]", "").replaceFirst("^0+(?!$)", "");

        if(valor.length() == 0){
            valor = "0,00";
        }else if(valor.length() == 1){
            valor = "0,0" + valor;
        }else if(valor.length() == 2){
            valor = "0,0" + valor;
        }else{
            valor = valor.substring(0,valor.length()-2) + "," + valor.substring(valor.length() -2);
        }

        return valor;

    }

}
