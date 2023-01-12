package com.aplikasi.perpustakaan.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.aplikasi.perpustakaan.model.Buku;
import com.aplikasi.perpustakaan.model.Peminjaman;
import com.aplikasi.perpustakaan.model.Pengguna;

@Database(entities = {Pengguna.class, Buku.class, Peminjaman.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoPengguna daoPengguna();
    public abstract DaoBuku daoBuku();
    public abstract DaoPeminjaman daoPeminjaman();
}
