package com.aplikasi.perpustakaan.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "pengguna")
public class Pengguna implements Serializable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "admin")
    private boolean admin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeByte(this.admin ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.username = source.readString();
        this.password = source.readString();
        this.admin = source.readByte() != 0;
    }

    public Pengguna() {
    }

    protected Pengguna(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.password = in.readString();
        this.admin = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Pengguna> CREATOR = new Parcelable.Creator<Pengguna>() {
        @Override
        public Pengguna createFromParcel(Parcel source) {
            return new Pengguna(source);
        }

        @Override
        public Pengguna[] newArray(int size) {
            return new Pengguna[size];
        }
    };
}
