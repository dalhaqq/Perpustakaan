package com.aplikasi.perpustakaan.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "buku")
public class Buku implements Serializable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "judul")
    private String judul;

    @ColumnInfo(name = "penulis")
    private String penulis;

    @ColumnInfo(name = "deskripsi")
    private String deskripsi;

    @ColumnInfo(name = "kategori")
    private String kategori;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.judul);
        dest.writeString(this.penulis);
        dest.writeString(this.deskripsi);
        dest.writeString(this.kategori);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.judul = source.readString();
        this.penulis = source.readString();
        this.deskripsi = source.readString();
        this.kategori = source.readString();
    }

    public Buku() {
    }

    protected Buku(Parcel in) {
        this.id = in.readInt();
        this.judul = in.readString();
        this.penulis = in.readString();
        this.deskripsi = in.readString();
        this.kategori = in.readString();
    }

    public static final Parcelable.Creator<Buku> CREATOR = new Parcelable.Creator<Buku>() {
        @Override
        public Buku createFromParcel(Parcel source) {
            return new Buku(source);
        }

        @Override
        public Buku[] newArray(int size) {
            return new Buku[size];
        }
    };
}
