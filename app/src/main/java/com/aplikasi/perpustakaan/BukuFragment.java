package com.aplikasi.perpustakaan;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.aplikasi.perpustakaan.adapter.AdminBukuAdapter;
import com.aplikasi.perpustakaan.adapter.BukuAdapter;
import com.aplikasi.perpustakaan.data.AppDatabase;
import com.aplikasi.perpustakaan.model.Buku;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BukuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BukuFragment extends Fragment {
    RecyclerView rvBuku;
    BukuAdapter bukuAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Buku> bukuList;
    AppDatabase db;

    public BukuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AdminBukuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BukuFragment newInstance() {
        BukuFragment fragment = new BukuFragment();
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
        return inflater.inflate(R.layout.fragment_buku, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvBuku = view.findViewById(R.id.rv_buku);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvBuku.setLayoutManager(linearLayoutManager);
        bukuList = new ArrayList<>();
        List<Buku> allBuku = Arrays.asList(db.daoBuku().selectBuku());
        bukuList.addAll(allBuku);
        bukuAdapter = new BukuAdapter(getActivity().getApplicationContext(), bukuList);
        rvBuku.setAdapter(bukuAdapter);
    }
}