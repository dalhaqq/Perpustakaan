package com.aplikasi.perpustakaan.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.aplikasi.perpustakaan.model.Pengguna;

@Dao
public interface DaoPengguna {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertPengguna(Pengguna pengguna);

    @Query("SELECT * FROM pengguna WHERE username = :username")
    Pengguna getPengguna(String username);

    @Query("SELECT * FROM pengguna WHERE id = :id")
    Pengguna getPengguna(int id);

    @Query("SELECT * FROM pengguna WHERE admin = 0")
    Pengguna[] getAllPengguna();

    @Delete
    void deletePengguna(Pengguna pengguna);
}
