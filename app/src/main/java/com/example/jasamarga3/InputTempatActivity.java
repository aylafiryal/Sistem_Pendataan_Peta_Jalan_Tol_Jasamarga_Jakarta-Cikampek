package com.example.jasamarga3;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class InputTempatActivity extends AppCompatActivity {

    private Agenda agenda;

    private Button next;
    private TextView jalan, kecamatan, kota, alamatLengkap;
    private EditText lokasi, KM;
    private String str_jalan, str_kecamatan, str_kota, str_lokasi, str_KM;
    private Intent intent, intent2;
    private Bundle args, args2;
    private List<Address> addresses;
    private Double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_tempat);

        get_latlng();

        intent2 = new Intent(InputTempatActivity.this, InputKegiatanActivity.class);
        args2 = new Bundle();

        showLokasi();

        next = (Button) findViewById(R.id.btn_detail_inputKegiatan);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });
    }

    public void get_latlng() {
        intent = getIntent();
        args = intent.getBundleExtra("BUNDLE");
        addresses = (List<Address>) args.getSerializable("DetailLokasi");
        lat = addresses.get(0).getLatitude();
        lng = addresses.get(0).getLongitude();
    }

    public void showLokasi() {
        str_jalan = addresses.get(0).getThoroughfare();
        str_kecamatan = addresses.get(0).getLocality();
        str_kota = addresses.get(0).getSubAdminArea();

        jalan = (TextView) findViewById(R.id.tv_detail_jalan);
        kecamatan = (TextView) findViewById(R.id.tv_detail_kecamatan);
        kota = (TextView) findViewById(R.id.tv_detail_kota);
        alamatLengkap = (TextView) findViewById(R.id.tv_detail_alamatLengkap);

        kota.setText(str_kota);
        kecamatan.setText(str_kecamatan);
        jalan.setText(str_jalan);
        alamatLengkap.setText(addresses.get(0).getAddressLine(0));
    }

    public void insertData() {
        lokasi = (EditText) findViewById(R.id.tv_detail_lokasi);
        KM = (EditText) findViewById(R.id.tv_detail_km);

        str_lokasi = lokasi.getText().toString() + " " + KM.getText().toString();

        agenda = new Agenda(str_jalan, str_kecamatan, str_kota, str_lokasi, false);

        args2.putSerializable("Agenda", agenda);
        args2.putDouble("Lat", lat);
        args2.putDouble("Lng", lng);
        intent2.putExtra("BUNDLE", args2);
        startActivity(intent2);
    }
}