package com.example.jasamarga3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailKegiatanActivity extends AppCompatActivity {

    JasamargaDatabase db;
    Agenda agenda;

    private Button view, ok;
    private TextView tanggal, kegiatan, tindak_lanjut, hasil, persetujuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kegiatan);

        db = new JasamargaDatabase(this);
        agenda = (Agenda) getIntent().getSerializableExtra("agenda_curr");

        init();
    }

    public void init() {
        tanggal = (TextView) findViewById(R.id.tv_detailkegiatan_tanggal);
        tanggal.setText(agenda.getTanggal());
        kegiatan = (TextView) findViewById(R.id.tv_detailkegiatan_kegiatan);
        kegiatan.setText(agenda.getKegiatan());
        tindak_lanjut = (TextView) findViewById(R.id.tv_detailkegiatan_tindakLanjut);
        tindak_lanjut.setText(agenda.getTindakLanjut());
        hasil = (TextView) findViewById(R.id.tv_detailkegiatan_hasil);
        hasil.setText(agenda.getHasil());
        persetujuan = (TextView) findViewById(R.id.tv_detailkegiatan_persetujuan);
        persetujuan.setText(agenda.getPersetujuan());
        view = (Button) findViewById(R.id.btn_detailkegiatan_dokumentasi);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailKegiatanActivity.this, DokumentasiActivity.class);
                intent.putExtra("agenda_curr", agenda);
                startActivity(intent);
            }
        });
        ok = (Button) findViewById(R.id.btn_detailkegiatan_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailKegiatanActivity.this, DaftarKegiatanActivity.class));
            }
        });
    }
}