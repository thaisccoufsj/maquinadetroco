package com.example.maquinadetroco.data.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.maquinadetroco.models.Caixa;
import com.example.maquinadetroco.models.HistoricoCaixa;

import java.util.List;

@Dao
public interface CaixaDAO {

    @Query("SELECT * FROM caixa LIMIT 1")
    List<Caixa> getCaixa();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateCaixa(Caixa caixa);

    @Insert
    Long insertCaixa(Caixa caixa);

    @Query("SELECT * FROM HISTORICO_CAIXA ORDER BY dataHora DESC")
    List<HistoricoCaixa> getHistorico();

    @Insert
    Long insertHistorico(HistoricoCaixa historicoCaixa);

    @Query("DELETE FROM HISTORICO_CAIXA where id = :id")
    void deleteHistorico(long id);

}
