package com.example.maquinadetroco.data.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.maquinadetroco.models.Caixa;

@Database(entities = {Caixa.class}, version = 1,exportSchema = false)
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
