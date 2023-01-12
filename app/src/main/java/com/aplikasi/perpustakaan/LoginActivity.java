package com.aplikasi.perpustakaan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aplikasi.perpustakaan.data.AppDatabase;
import com.aplikasi.perpustakaan.helper.Constants;
import com.aplikasi.perpustakaan.helper.Session;
import com.aplikasi.perpustakaan.model.Pengguna;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvDaftar;

    AppDatabase db;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new Session(getApplicationContext());
        if (session.isLogin()) {
            if (session.isAdmin()) {
                startActivity(new Intent(LoginActivity.this, AdminActivity.class));
            } else {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
            finish();
        }

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, Constants.DATABASE_NAME).allowMainThreadQueries().build();

        Pengguna cekAdmin = db.daoPengguna().getPengguna("admin");
        if (cekAdmin == null) {
            Pengguna admin = new Pengguna();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setAdmin(true);
            db.daoPengguna().insertPengguna(admin);
        }

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvDaftar = findViewById(R.id.tv_daftar);

        tvDaftar.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, DaftarActivity.class));
        });

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Username dan password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            Pengguna pengguna = db.daoPengguna().getPengguna(username);
            if (pengguna == null) {
                Toast.makeText(LoginActivity.this, "Username tidak ditemukan", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pengguna.getPassword().equals(password)) {
                Toast.makeText(LoginActivity.this, "Password salah", Toast.LENGTH_SHORT).show();
                return;
            }

            session.setSession(pengguna);

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        });
    }
}