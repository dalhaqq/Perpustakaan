package com.aplikasi.perpustakaan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class AdminPeminjamanAdapter extends RecyclerView.Adapter<AdminPeminjamanAdapter.ViewHolder> {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_peminjaman, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Peminjaman peminjaman = peminjamanList.get(position);
        Buku buku = db.daoBuku().getBuku(peminjaman.getIdBuku());
        Pengguna peminjam = db.daoPengguna().getPengguna(peminjaman.getIdPengguna());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");

        holder.tvJudul.setText(buku.getJudul());
        holder.tvTglPinjam.setText("Tanggal pinjam: " + sdf.format(peminjaman.getTanggalPinjam()));
        holder.tvTglKembali.setText("Tanggal kembali: " + sdf.format(peminjaman.getTanggalKembali()));
        holder.tvPeminjam.setText("Peminjam: " + peminjam.getUsername());
    }

    @Override
    public int getItemCount() {
        return peminjamanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvTglPinjam, tvTglKembali, tvPeminjam;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvTglPinjam = itemView.findViewById(R.id.tv_tgl_pinjam);
            tvTglKembali = itemView.findViewById(R.id.tv_tgl_kembali);
            tvPeminjam = itemView.findViewById(R.id.tv_peminjam);
        }
    }

    public AdminPeminjamanAdapter(Context context, ArrayList<Peminjaman> peminjamanList) {
        this.context = context;
        this.peminjamanList = peminjamanList;
    }
}
