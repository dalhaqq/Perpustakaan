package com.aplikasi.perpustakaan.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.aplikasi.perpustakaan.model.Buku;

@Dao
public interface DaoBuku {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBuku(Buku buku);

    @Query("SELECT * FROM buku")
    Buku[] selectBuku();

    @Query("SELECT * FROM buku WHERE id = :id LIMIT 1")
    Buku getBuku(int id);

    @Delete
    void deleteBuku(Buku buku);

    @Update
    void updateBuku(Buku buku);
}
