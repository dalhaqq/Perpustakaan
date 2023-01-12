package com.aplikasi.perpustakaan.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.aplikasi.perpustakaan.model.Peminjaman;

@Dao
public interface DaoPeminjaman {
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    void insertPeminjaman(Peminjaman peminjaman);

    @Query("SELECT * FROM peminjaman")
    Peminjaman[] selectAllPeminjaman();

    @Query("SELECT * FROM peminjaman WHERE id_pengguna = :id_pengguna")
    Peminjaman[] selectPeminjamanByIdPengguna(int id_pengguna);
}
