package com.example.maquinadetroco;

import com.example.maquinadetroco.models.Caixa;

public class StateView {

    int code = 0;
    String message = "";

    public StateView(int code,String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public StateView(int code){
        this.code = code;
    }

}