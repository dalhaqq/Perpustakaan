package com.aplikasi.perpustakaan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aplikasi.perpustakaan.adapter.AdminPeminjamanAdapter;
import com.aplikasi.perpustakaan.adapter.PeminjamanAdapter;
import com.aplikasi.perpustakaan.data.AppDatabase;
import com.aplikasi.perpustakaan.model.Peminjaman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminPeminjamanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminPeminjamanFragment extends Fragment {
    RecyclerView rvPeminjaman;
    AdminPeminjamanAdapter adminPeminjamanAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Peminjaman> peminjamanList;
    AppDatabase db;
    public AdminPeminjamanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AdminPeminjamanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminPeminjamanFragment newInstance() {
        AdminPeminjamanFragment fragment = new AdminPeminjamanFragment();
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
        return inflater.inflate(R.layout.fragment_admin_peminjaman, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPeminjaman = view.findViewById(R.id.rv_peminjaman);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvPeminjaman.setLayoutManager(linearLayoutManager);
        peminjamanList = new ArrayList<>();
        List<Peminjaman> allPeminjaman = Arrays.asList(db.daoPeminjaman().selectAllPeminjaman());
        peminjamanList.addAll(allPeminjaman);
        adminPeminjamanAdapter = new AdminPeminjamanAdapter(getActivity().getApplicationContext(), peminjamanList);
        rvPeminjaman.setAdapter(adminPeminjamanAdapter);
    }
}