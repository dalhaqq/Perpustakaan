package com.aplikasi.perpustakaan.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.aplikasi.perpustakaan.helper.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "peminjaman")
public class Peminjaman implements Serializable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "id_buku")
    private int idBuku;

    @ColumnInfo(name = "id_pengguna")
    private int idPengguna;

    @ColumnInfo(name = "tanggal_pinjam")
    @TypeConverters({DateConverter.class})
    private Date tanggalPinjam;

    @ColumnInfo(name = "tanggal_kembali")
    @TypeConverters({DateConverter.class})
    private Date tanggalKembali;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }

    public int getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(int idPengguna) {
        this.idPengguna = idPengguna;
    }

    public Date getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(Date tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public Date getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(Date tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.idBuku);
        dest.writeInt(this.idPengguna);
        dest.writeLong(this.tanggalPinjam != null ? this.tanggalPinjam.getTime() : -1);
        dest.writeLong(this.tanggalKembali != null ? this.tanggalKembali.getTime() : -1);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.idBuku = source.readInt();
        this.idPengguna = source.readInt();
        long tmpTanggalPinjam = source.readLong();
        this.tanggalPinjam = tmpTanggalPinjam == -1 ? null : new Date(tmpTanggalPinjam);
        long tmpTanggalKembali = source.readLong();
        this.tanggalKembali = tmpTanggalKembali == -1 ? null : new Date(tmpTanggalKembali);
    }

    public Peminjaman() {
    }

    protected Peminjaman(Parcel in) {
        this.id = in.readInt();
        this.idBuku = in.readInt();
        this.idPengguna = in.readInt();
        long tmpTanggalPinjam = in.readLong();
        this.tanggalPinjam = tmpTanggalPinjam == -1 ? null : new Date(tmpTanggalPinjam);
        long tmpTanggalKembali = in.readLong();
        this.tanggalKembali = tmpTanggalKembali == -1 ? null : new Date(tmpTanggalKembali);
    }

    public static final Parcelable.Creator<Peminjaman> CREATOR = new Parcelable.Creator<Peminjaman>() {
        @Override
        public Peminjaman createFromParcel(Parcel source) {
            return new Peminjaman(source);
        }

        @Override
        public Peminjaman[] newArray(int size) {
            return new Peminjaman[size];
        }
    };
}
