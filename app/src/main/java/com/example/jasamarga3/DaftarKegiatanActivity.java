package com.example.jasamarga3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
    private TextView tv_nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kegiatan);

        recyclerView = findViewById(R.id.rv_daftarLokasiKegiatan);
        tv_nodata = findViewById(R.id.tv_daftarLokasiKegiatan_noData);

        get_data();
        no_data();
        adapter = new DaftarLokasiKegiatanAdapter(db, agendaList, listLokasi, this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void no_data(){
        if(agendaList.isEmpty()){
            tv_nodata.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tv_nodata.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
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