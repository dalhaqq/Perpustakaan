package com.aplikasi.perpustakaan.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Buku> bukuList;
    private AppDatabase db;
    private Session session;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, Constants.DATABASE_NAME).allowMainThreadQueries().build();
        session = new Session(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buku, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Buku buku = bukuList.get(position);
        holder.tvJudul.setText(buku.getJudul());
        holder.tvPenulis.setText(buku.getPenulis());
        holder.tvKategori.setText(buku.getKategori());
        holder.tvDeskripsi.setText(buku.getDeskripsi());

        holder.btnPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
                final View dialogView = inflater.inflate(R.layout.dialog_pinjam, null);
                final AlertDialog dialog = new AlertDialog.Builder(holder.itemView.getContext()).create();
                dialog.setView(dialogView);
                ((android.widget.TextView) dialogView.findViewById(R.id.tv_judul)).setText(buku.getJudul());
                dialogView.findViewById(R.id.btn_batal).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialogView.findViewById(R.id.btn_pinjam).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Peminjaman peminjaman = new Peminjaman();
                        peminjaman.setIdPengguna(session.getId());
                        peminjaman.setIdBuku(buku.getId());
                        String tglPinjam = ((android.widget.EditText) dialogView.findViewById(R.id.et_tgl_pinjam)).getText().toString();
                        String tglKembali = ((android.widget.EditText) dialogView.findViewById(R.id.et_tgl_kembali)).getText().toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            Date datePinjam = sdf.parse(tglPinjam);
                            Date dateKembali = sdf.parse(tglKembali);
                            peminjaman.setTanggalPinjam(datePinjam);
                            peminjaman.setTanggalKembali(dateKembali);
                            db.daoPeminjaman().insertPeminjaman(peminjaman);
                            Toast.makeText(context, "Berhasil meminjam buku", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(context, "Format tanggal salah!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bukuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvPenulis, tvKategori, tvDeskripsi;
        Button btnPinjam;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnPinjam = itemView.findViewById(R.id.btn_pinjam);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvPenulis = itemView.findViewById(R.id.tv_penulis);
            tvKategori = itemView.findViewById(R.id.tv_kategori);
            tvDeskripsi = itemView.findViewById(R.id.tv_deskripsi);
        }
    }

    public BukuAdapter(Context context, ArrayList<Buku> bukuList) {
        this.context = context;
        this.bukuList = bukuList;
    }
}
