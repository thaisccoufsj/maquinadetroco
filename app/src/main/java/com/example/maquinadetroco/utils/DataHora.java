package com.example.maquinadetroco.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public final class DataHora {

    public static String DataHoraAtual(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",Funcoes.getLocale());
        Date date = new Date();
        return dateFormat.format(date);
    }

}