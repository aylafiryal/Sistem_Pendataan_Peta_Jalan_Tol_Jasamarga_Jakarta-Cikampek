package com.example.jasamarga3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditKegiatanActivity extends AppCompatActivity {

    private JasamargaDatabase db;
    private Agenda agenda;
    private String id_agenda;

    private Button btn_simpan, btn_kalender;
    private TextView tv_tanggal;
    private EditText kegiatan, tindak_lanjut, hasil, persetujuan;
    private String str_tanggal, str_kegiatan, str_tindakLanjut, str_hasil, str_persetujuan;
    private Boolean btn_Kalender_click;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_kegiatan);

        agenda = (Agenda) getIntent().getSerializableExtra("agenda_curr");
        db = new JasamargaDatabase(this);
        id_agenda = db.get_agendaID(agenda.getKegiatan(), agenda.getLokasiKM());

        init();
        tanggalChanged();
        setTanggal();
        simpanKegiatan();
    }

    public void init() {
        btn_kalender = (Button) findViewById(R.id.btn_kegiatan_kalender);
        tv_tanggal = (TextView) findViewById(R.id.tv_kegiatan_tanggal);
        tv_tanggal.setText(agenda.getTanggal());
        kegiatan = (EditText) findViewById(R.id.tv_kegiatan_kegiatan);
        kegiatan.setText(agenda.getKegiatan());
        tindak_lanjut = (EditText) findViewById(R.id.tv_kegiatan_tindaklanjut);
        tindak_lanjut.setText(agenda.getTindakLanjut());
        hasil = (EditText) findViewById(R.id.tv_kegiatan_hasil);
        hasil.setText(agenda.getHasil());
        persetujuan = (EditText) findViewById(R.id.tv_kegiatan_persetujuan);
        persetujuan.setText(agenda.getPersetujuan());
    }

    public void tanggalChanged(){
        if(agenda.getTanggal() == null){
            tv_tanggal.setVisibility(View.GONE);
            btn_kalender.setVisibility(View.VISIBLE);
        }
        if(str_tanggal != null){
            tv_tanggal.setVisibility(View.VISIBLE);
            btn_kalender.setVisibility(View.GONE);
        }
    }

    public void setTanggal() {

        Calendar kalender = Calendar.getInstance();
        final int year = kalender.get(Calendar.YEAR);
        final int month = kalender.get(Calendar.MONTH);
        final int day = kalender.get(Calendar.DAY_OF_MONTH);

        tv_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(EditKegiatanActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                            kalender.set(year, month, day);
                            str_tanggal = sdf.format(kalender.getTime());
                            tv_tanggal.setText(str_tanggal);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
            }
        });

        btn_kalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(EditKegiatanActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                            kalender.set(year, month, day);
                            str_tanggal = sdf.format(kalender.getTime());
                            tanggalChanged();
                            tv_tanggal.setText(str_tanggal);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
            }
        });

    }

    public void updateData() {
        str_kegiatan = kegiatan.getText().toString();
        str_tindakLanjut = tindak_lanjut.getText().toString();
        str_hasil = hasil.getText().toString();
        str_persetujuan = persetujuan.getText().toString();

        agenda = new Agenda(str_tanggal, str_kegiatan, str_tindakLanjut, str_hasil, str_persetujuan, agenda.getJalan(), agenda.getKecamatan(), agenda.getKota(), agenda.getLokasiKM());
        db.update_data(id_agenda, agenda);
    }

    public void simpanKegiatan() {
        btn_simpan = (Button) findViewById(R.id.btn_kegiatan_simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
                startActivity(new Intent(EditKegiatanActivity.this, DaftarKegiatanActivity.class));
            }
        });
    }
}
