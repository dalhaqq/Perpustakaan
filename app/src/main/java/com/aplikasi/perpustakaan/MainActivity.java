package com.aplikasi.perpustakaan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aplikasi.perpustakaan.data.AppDatabase;
import com.aplikasi.perpustakaan.helper.Constants;
import com.aplikasi.perpustakaan.helper.Session;
import com.aplikasi.perpustakaan.model.Pengguna;

public class MainActivity extends AppCompatActivity {
    Session session;
    AppDatabase db;

    FragmentManager fragmentManager;
    BukuFragment bukuFragment;
    PeminjamanFragment peminjamanFragment;

    HeaderFragment headerFragment;

    View navBuku, navPeminjaman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new Session(getApplicationContext());
        if (!session.isLogin()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        if (session.isAdmin()) {
            startActivity(new Intent(MainActivity.this, AdminActivity.class));
            finish();
        }

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, Constants.DATABASE_NAME).allowMainThreadQueries().build();

        fragmentManager = getSupportFragmentManager();
        bukuFragment = new BukuFragment();
        peminjamanFragment = new PeminjamanFragment();
        navBuku = findViewById(R.id.buku);
        navPeminjaman = findViewById(R.id.peminjaman);
        fragmentManager.beginTransaction().add(R.id.fragment_container, bukuFragment).commit();

        headerFragment = (HeaderFragment) fragmentManager.findFragmentById(R.id.nav_container);
        headerFragment.setTitle("Buku");

        navBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerFragment.setTitle("Buku");
                fragmentManager.beginTransaction().replace(R.id.fragment_container, bukuFragment).addToBackStack(null).commit();
            }
        });
        navPeminjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerFragment.setTitle("Peminjaman");
                fragmentManager.beginTransaction().replace(R.id.fragment_container, peminjamanFragment).addToBackStack(null).commit();
            }
        });
    }
}