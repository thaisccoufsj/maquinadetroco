package com.example.maquinadetroco.data.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.maquinadetroco.models.Caixa;
import com.example.maquinadetroco.models.HistoricoCaixa;

@Database(entities = {Caixa.class, HistoricoCaixa.class}, version = 2,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static final String DB_NAME = "maquina_trocos";
    private volatile static AppDataBase instance;


    public static synchronized AppDataBase getInstance(Context context){
        if(instance == null){
            instance = buildDatabase(context);
        }

        return instance;
    }

    public static AppDataBase buildDatabase(Context context){
        return Room.databaseBuilder(context.getApplicationContext(),
                AppDataBase.class,DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    public abstract CaixaDAO caixaDAO();

}
