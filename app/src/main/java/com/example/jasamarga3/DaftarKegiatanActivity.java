package com.example.jasamarga3;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DaftarKegiatanActivity extends AppCompatActivity implements DaftarLokasiKegiatanAdapter.TambahKegiatan {

    private JasamargaDatabase db;
    private ArrayList<Agenda> agendaList;
    private ArrayList<Lokasi> listLokasi;
    private DaftarLokasiKegiatanAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kegiatan);

        get_data();

        recyclerView = findViewById(R.id.rv_daftarLokasiKegiatan);
        adapter = new DaftarLokasiKegiatanAdapter(db, agendaList, listLokasi, this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void get_data() {
        agendaList = new ArrayList<>();
        db = new JasamargaDatabase(this);
        agendaList = db.read_data();
        listLokasi = db.read_lokasi();
    }

    @Override
    public void tambahKegiatan(Lokasi lokasi) {
        Intent intent = new Intent(this, TambahKegiatanActivity.class);
        intent.putExtra("lokasi_curr", lokasi);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DaftarKegiatanActivity.this, MapsActivity.class));
        finish();
    }
}