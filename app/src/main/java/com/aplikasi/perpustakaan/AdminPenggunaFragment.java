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

import com.aplikasi.perpustakaan.adapter.AdminPenggunaAdapter;
import com.aplikasi.perpustakaan.data.AppDatabase;
import com.aplikasi.perpustakaan.model.Pengguna;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminPenggunaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminPenggunaFragment extends Fragment {
    RecyclerView rvPengguna;
    AdminPenggunaAdapter penggunaAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Pengguna> penggunaList;
    AppDatabase db;
    public AdminPenggunaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AdminPenggunaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminPenggunaFragment newInstance() {
        AdminPenggunaFragment fragment = new AdminPenggunaFragment();
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
        return inflater.inflate(R.layout.fragment_admin_pengguna, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPengguna = view.findViewById(R.id.rv_pengguna);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvPengguna.setLayoutManager(linearLayoutManager);
        penggunaList = new ArrayList<>();
        List<Pengguna> allPengguna = Arrays.asList(db.daoPengguna().getAllPengguna());
        penggunaList.addAll(allPengguna);
        penggunaAdapter = new AdminPenggunaAdapter(getActivity().getApplicationContext(), penggunaList);
        rvPengguna.setAdapter(penggunaAdapter);
    }
}