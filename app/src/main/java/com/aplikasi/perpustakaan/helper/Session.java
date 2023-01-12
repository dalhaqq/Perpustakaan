package com.aplikasi.perpustakaan.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.aplikasi.perpustakaan.model.Pengguna;

public class Session {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Session(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setSession(Pengguna pengguna) {
        editor.putInt(Constants.KEY_SESSION_ID, pengguna.getId());
        editor.putString(Constants.KEY_SESSION_USERNAME, pengguna.getUsername());
        editor.putBoolean(Constants.KEY_SESSION_ADMIN, pengguna.isAdmin());
        editor.apply();
    }

    public void removeSession() {
        editor.remove(Constants.KEY_SESSION_ID);
        editor.remove(Constants.KEY_SESSION_USERNAME);
        editor.remove(Constants.KEY_SESSION_ADMIN);
        editor.apply();
    }

    public boolean isLogin() {
        return sharedPreferences.contains(Constants.KEY_SESSION_ID);
    }

    public boolean isAdmin() {
        return sharedPreferences.getBoolean(Constants.KEY_SESSION_ADMIN, false);
    }

    public int getId() {
        return sharedPreferences.getInt(Constants.KEY_SESSION_ID, 0);
    }

    public String getUsername() {
        return sharedPreferences.getString(Constants.KEY_SESSION_USERNAME, "");
    }
}
