package com.aplikasi.perpustakaan.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.aplikasi.perpustakaan.R;
import com.aplikasi.perpustakaan.data.AppDatabase;
import com.aplikasi.perpustakaan.helper.Constants;
import com.aplikasi.perpustakaan.model.Buku;
import com.aplikasi.perpustakaan.model.Pengguna;

import java.util.ArrayList;

public class AdminBukuAdapter extends RecyclerView.Adapter<AdminBukuAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Buku> bukuList;
    private AppDatabase db;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, Constants.DATABASE_NAME).allowMainThreadQueries().build();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_buku, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Buku buku = bukuList.get(position);
        holder.tvJudul.setText(buku.getJudul());
        holder.tvPenulis.setText(buku.getPenulis());
        holder.tvKategori.setText(buku.getKategori());
        holder.tvDeskripsi.setText(buku.getDeskripsi());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.daoBuku().deleteBuku(buku);
                bukuList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
                final View dialogView = inflater.inflate(R.layout.dialog_buku, null);
                final AlertDialog dialog = new AlertDialog.Builder(holder.itemView.getContext()).create();
                dialog.setView(dialogView);
                ((android.widget.EditText) dialogView.findViewById(R.id.et_judul)).setText(buku.getJudul());
                ((android.widget.EditText) dialogView.findViewById(R.id.et_penulis)).setText(buku.getPenulis());
                ((android.widget.EditText) dialogView.findViewById(R.id.et_kategori)).setText(buku.getKategori());
                ((android.widget.EditText) dialogView.findViewById(R.id.et_deskripsi)).setText(buku.getDeskripsi());
                dialogView.findViewById(R.id.btn_batal).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialogView.findViewById(R.id.btn_simpan).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buku.setJudul(((android.widget.EditText) dialogView.findViewById(R.id.et_judul)).getText().toString());
                        buku.setPenulis(((android.widget.EditText) dialogView.findViewById(R.id.et_penulis)).getText().toString());
                        buku.setDeskripsi(((android.widget.EditText) dialogView.findViewById(R.id.et_deskripsi)).getText().toString());
                        buku.setKategori(((android.widget.EditText) dialogView.findViewById(R.id.et_kategori)).getText().toString());
                        db.daoBuku().updateBuku(buku);
                        bukuList.set(position, buku);
                        notifyDataSetChanged();
                        dialog.dismiss();
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
        ImageView imgEdit, imgDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvPenulis = itemView.findViewById(R.id.tv_penulis);
            tvKategori = itemView.findViewById(R.id.tv_kategori);
            tvDeskripsi = itemView.findViewById(R.id.tv_deskripsi);
            imgEdit = itemView.findViewById(R.id.img_edit);
            imgDelete = itemView.findViewById(R.id.img_delete);
        }
    }

    public AdminBukuAdapter(Context context, ArrayList<Buku> bukuList) {
        this.context = context;
        this.bukuList = bukuList;
    }

    public void addBuku(Buku buku) {
        bukuList.add(buku);
        notifyDataSetChanged();
    }
}
