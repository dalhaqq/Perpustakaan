package com.aplikasi.perpustakaan;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aplikasi.perpustakaan.adapter.AdminBukuAdapter;
import com.aplikasi.perpustakaan.data.AppDatabase;
import com.aplikasi.perpustakaan.model.Buku;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminBukuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminBukuFragment extends Fragment {
    RecyclerView rvBuku;
    AdminBukuAdapter bukuAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Buku> bukuList;
    AppDatabase db;
    FloatingActionButton btnTambahBuku;

    public AdminBukuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AdminBukuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminBukuFragment newInstance() {
        AdminBukuFragment fragment = new AdminBukuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                AppDatabase.class, "perpustakaan.db").allowMainThreadQueries().build();
        return inflater.inflate(R.layout.fragment_admin_buku, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnTambahBuku = view.findViewById(R.id.btn_tambah);
        rvBuku = view.findViewById(R.id.rv_buku);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvBuku.setLayoutManager(linearLayoutManager);
        bukuList = new ArrayList<>();
        List<Buku> allBuku = Arrays.asList(db.daoBuku().selectBuku());
        bukuList.addAll(allBuku);
        bukuAdapter = new AdminBukuAdapter(getActivity().getApplicationContext(), bukuList);
        rvBuku.setAdapter(bukuAdapter);
        btnTambahBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                buat dialog menggunakan buku_dialog
                LayoutInflater inflater = LayoutInflater.from(getContext());
                final View dialogView = inflater.inflate(R.layout.dialog_buku, null);
                final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
                dialog.setView(dialogView);
                dialogView.findViewById(R.id.btn_batal).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialogView.findViewById(R.id.btn_simpan).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Buku buku = new Buku();
                        buku.setJudul(((android.widget.EditText) dialogView.findViewById(R.id.et_judul)).getText().toString());
                        buku.setPenulis(((android.widget.EditText) dialogView.findViewById(R.id.et_penulis)).getText().toString());
                        buku.setDeskripsi(((android.widget.EditText) dialogView.findViewById(R.id.et_deskripsi)).getText().toString());
                        buku.setKategori(((android.widget.EditText) dialogView.findViewById(R.id.et_kategori)).getText().toString());
                        db.daoBuku().insertBuku(buku);
                        bukuAdapter.addBuku(buku);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}