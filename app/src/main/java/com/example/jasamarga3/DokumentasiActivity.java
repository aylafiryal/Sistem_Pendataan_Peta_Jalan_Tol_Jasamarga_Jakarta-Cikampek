package com.example.jasamarga3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DokumentasiActivity extends AppCompatActivity {

    private JasamargaDatabase db;
    private Agenda agenda;
    private ArrayList<byte[]> imagesUri;
    private int id;
    private DokumentasiAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokumentasi);

        db = new JasamargaDatabase(this);
        agenda = (Agenda) getIntent().getSerializableExtra("agenda_curr");
        id = Integer.parseInt(db.get_agendaID(agenda.getKegiatan(), agenda.getLokasiKM()));
        imagesUri = db.read_dokumentasi(id);

        adapter = new DokumentasiAdapter(imagesUri, this);
        recyclerView = findViewById(R.id.rv_daftarDokumentasi);
        recyclerView.setAdapter(adapter);
    }
}