package com.aplikasi.perpustakaan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aplikasi.perpustakaan.data.AppDatabase;
import com.aplikasi.perpustakaan.helper.Constants;
import com.aplikasi.perpustakaan.helper.Session;
import com.aplikasi.perpustakaan.model.Pengguna;

public class DaftarActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnDaftar;
    TextView tvLogin;
    AppDatabase db;

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        session = new Session(getApplicationContext());
        if (session.isLogin()) {
            if (session.isAdmin()) {
                startActivity(new Intent(DaftarActivity.this, AdminActivity.class));
            } else {
                startActivity(new Intent(DaftarActivity.this, MainActivity.class));
            }
            finish();
        }

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, Constants.DATABASE_NAME).allowMainThreadQueries().build();

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnDaftar = findViewById(R.id.btn_daftar);
        tvLogin = findViewById(R.id.tv_login);

        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(DaftarActivity.this, LoginActivity.class));
        });

        btnDaftar.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(DaftarActivity.this, "Username dan password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            Pengguna pengguna = db.daoPengguna().getPengguna(username);
            if (pengguna != null) {
                Toast.makeText(DaftarActivity.this, "Username sudah digunakan", Toast.LENGTH_SHORT).show();
                return;
            }

            pengguna = new Pengguna();
            pengguna.setUsername(username);
            pengguna.setPassword(password);
            pengguna.setAdmin(false);

            Pengguna finalPengguna = pengguna;
            new AsyncTask<Void, Void, Long>(){
                @Override
                protected Long doInBackground(Void... voids) {
                    return db.daoPengguna().insertPengguna(finalPengguna);
                }

                @Override
                protected void onPostExecute(Long aLong) {
                    super.onPostExecute(aLong);
                    if (aLong > 0) {
                        Toast.makeText(DaftarActivity.this, "Berhasil mendaftar", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DaftarActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(DaftarActivity.this, "Gagal mendaftar", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        });
    }
}