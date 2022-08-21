package com.example.jasamarga3;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
    private TextView tv_nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokumentasi);

        recyclerView = findViewById(R.id.rv_daftarDokumentasi);
        tv_nodata = findViewById(R.id.tv_daftarDokumentasi_noData);

        db = new JasamargaDatabase(this);
        agenda = (Agenda) getIntent().getSerializableExtra("agenda_curr");
        id = Integer.parseInt(db.get_agendaID(agenda.getKegiatan(), agenda.getLokasiKM()));
        imagesUri = db.read_dokumentasi(id);

        no_data();

        adapter = new DokumentasiAdapter(imagesUri, this);
        recyclerView.setAdapter(adapter);
    }

    public void no_data(){
        if(imagesUri.isEmpty()){
            tv_nodata.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tv_nodata.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}