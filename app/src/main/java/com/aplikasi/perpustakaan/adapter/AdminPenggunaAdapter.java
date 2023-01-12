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

public class AdminPenggunaAdapter extends RecyclerView.Adapter<AdminPenggunaAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Pengguna> penggunaList;
    private AppDatabase db;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, Constants.DATABASE_NAME).allowMainThreadQueries().build();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_pengguna, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pengguna pengguna = penggunaList.get(position);
        holder.tvUsername.setText(pengguna.getUsername());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.daoPengguna().deletePengguna(pengguna);
                penggunaList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return penggunaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername;
        ImageView imgDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tv_username);
            imgDelete = itemView.findViewById(R.id.img_delete);
        }
    }

    public AdminPenggunaAdapter(Context context, ArrayList<Pengguna> penggunaList) {
        this.context = context;
        this.penggunaList = penggunaList;
    }
}
