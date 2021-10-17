package com.example.maquinadetroco.data.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.maquinadetroco.models.Caixa;

import java.util.List;

@Dao
interface CaixaDAO {

    @Query("SELECT * FROM caixa LIMIT 1")
    List<Caixa> getCaixa();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateCaixa(Caixa caixa);

    @Insert
    Long insertCaixa(Caixa caixa);

}
