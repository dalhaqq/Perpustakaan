package com.aplikasi.perpustakaan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aplikasi.perpustakaan.data.AppDatabase;
import com.aplikasi.perpustakaan.helper.Constants;
import com.aplikasi.perpustakaan.helper.Session;
import com.aplikasi.perpustakaan.model.Pengguna;

public class AdminActivity extends AppCompatActivity {
    Session session;
    AppDatabase db;

    FragmentManager fragmentManager;
    AdminBukuFragment adminBukuFragment;
    AdminPenggunaFragment adminPenggunaFragment;
    AdminPeminjamanFragment adminPeminjamanFragment;

    HeaderFragment headerFragment;

    View navBuku, navPengguna, navPeminjaman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        session = new Session(getApplicationContext());
        if (!session.isLogin()) {
            startActivity(new Intent(AdminActivity.this, LoginActivity.class));
            finish();
        }

        if (!session.isAdmin()) {
            startActivity(new Intent(AdminActivity.this, MainActivity.class));
            finish();
        }

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, Constants.DATABASE_NAME).allowMainThreadQueries().build();

        fragmentManager = getSupportFragmentManager();
        adminBukuFragment = new AdminBukuFragment();
        adminPenggunaFragment = new AdminPenggunaFragment();
        adminPeminjamanFragment = new AdminPeminjamanFragment();
        navBuku = findViewById(R.id.buku);
        navPengguna = findViewById(R.id.pengguna);
        navPeminjaman = findViewById(R.id.peminjaman);
        fragmentManager.beginTransaction().add(R.id.fragment_container, adminBukuFragment).commit();

        headerFragment = (HeaderFragment) fragmentManager.findFragmentById(R.id.nav_container);
        headerFragment.setTitle("Buku");

        navBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerFragment.setTitle("Buku");
                fragmentManager.beginTransaction().replace(R.id.fragment_container, adminBukuFragment).addToBackStack(null).commit();
            }
        });
        navPeminjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerFragment.setTitle("Peminjaman");
                fragmentManager.beginTransaction().replace(R.id.fragment_container, adminPeminjamanFragment).addToBackStack(null).commit();
            }
        });
        navPengguna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerFragment.setTitle("Pengguna");
                fragmentManager.beginTransaction().replace(R.id.fragment_container, adminPenggunaFragment).addToBackStack(null).commit();
            }
        });
    }
}