package com.aplikasi.perpustakaan.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.aplikasi.perpustakaan.R;
import com.aplikasi.perpustakaan.data.AppDatabase;
import com.aplikasi.perpustakaan.helper.Constants;
import com.aplikasi.perpustakaan.helper.Session;
import com.aplikasi.perpustakaan.model.Buku;
import com.aplikasi.perpustakaan.model.Peminjaman;
import com.aplikasi.perpustakaan.model.Pengguna;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PeminjamanAdapter extends RecyclerView.Adapter<PeminjamanAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Peminjaman> peminjamanList;
    private AppDatabase db;
    private Session session;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, Constants.DATABASE_NAME).allowMainThreadQueries().build();
        session = new Session(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_peminjaman, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Peminjaman peminjaman = peminjamanList.get(position);
        Buku buku = db.daoBuku().getBuku(peminjaman.getIdBuku());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");

        holder.tvJudul.setText(buku.getJudul());
        holder.tvTglPinjam.setText("Tanggal pinjam: " + sdf.format(peminjaman.getTanggalPinjam()));
        holder.tvTglKembali.setText("Tanggal kembali: " + sdf.format(peminjaman.getTanggalKembali()));
    }

    @Override
    public int getItemCount() {
        return peminjamanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvTglPinjam, tvTglKembali;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvTglPinjam = itemView.findViewById(R.id.tv_tgl_pinjam);
            tvTglKembali = itemView.findViewById(R.id.tv_tgl_kembali);
        }
    }

    public PeminjamanAdapter(Context context, ArrayList<Peminjaman> peminjamanList) {
        this.context = context;
        this.peminjamanList = peminjamanList;
    }
}
