package com.example.jasamarga3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class InputKegiatanActivity extends AppCompatActivity {

    private JasamargaDatabase db;
    private Agenda agenda, getAgenda;

    private Intent intent;
    private Bundle args;
    private LatLng latLng;
    private Double lat, lng;

    private Button btn_kalender, btn_simpan, btn_uploadfoto;
    private TextView tv_tanggal, nama_file;
    private Boolean btn_Kalender_click = false;
    private EditText kegiatan, tindak_lanjut, hasil, persetujuan;
    private String str_tanggal, str_kegiatan, str_tindakLanjut, str_hasil, str_persetujuan;

    private final int PICK_IMAGE_MULTIPLE = 1;
    private ArrayList<Uri> mArrayUri;
    private int cout;

    private int id_dokumentasi;

    private ArrayList<LatLng> latLngList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_kegiatan);

        db = new JasamargaDatabase(this);

        intent = getIntent();
        args = intent.getBundleExtra("BUNDLE");
        getAgenda = (Agenda) args.getSerializable("Agenda");
        lat = args.getDouble("Lat");
        lng = args.getDouble("Lng");
        latLng = new LatLng(lat, lng);

        mArrayUri = new ArrayList<>();
        latLngList = new ArrayList<>();

        init();
        tanggalChanged();
        setTanggal();
        uploadDokumentasi();
        simpanKegiatan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            if (data.getClipData() != null) {
                cout = data.getClipData().getItemCount();
                nama_file.setText(String.valueOf(cout));
                for (int i = 0; i < cout; i++) {
                    Uri imageurl = data.getClipData().getItemAt(i).getUri();
                    mArrayUri.add(imageurl);
                }
            } else {
                Uri imageurl = data.getData();
                mArrayUri.add(imageurl);
            }
        }
    }

    public void init() {
        btn_kalender = (Button) findViewById(R.id.btn_kegiatan_kalender);
        tv_tanggal = (TextView) findViewById(R.id.tv_kegiatan_tanggal);
        nama_file = (TextView) findViewById(R.id.tv_kegiatan_namaFile);
        kegiatan = (EditText) findViewById(R.id.tv_kegiatan_kegiatan);
        tindak_lanjut = (EditText) findViewById(R.id.tv_kegiatan_tindaklanjut);
        hasil = (EditText) findViewById(R.id.tv_kegiatan_hasil);
        persetujuan = (EditText) findViewById(R.id.tv_kegiatan_persetujuan);
        btn_simpan = (Button) findViewById(R.id.btn_kegiatan_simpan);
        btn_uploadfoto = (Button) findViewById(R.id.btn_kegiatan_uploadfoto);
    }

    public void tanggalChanged() {
        if (btn_Kalender_click.booleanValue() == true) {
            tv_tanggal.setVisibility(View.VISIBLE);
            btn_kalender.setVisibility(View.GONE);
        }
        if (btn_Kalender_click.booleanValue() == false) {
            tv_tanggal.setVisibility(View.GONE);
            btn_kalender.setVisibility(View.VISIBLE);
        }
    }

    public void setTanggal() {

        Calendar kalender = Calendar.getInstance();
        final int year = kalender.get(Calendar.YEAR);
        final int month = kalender.get(Calendar.MONTH);
        final int day = kalender.get(Calendar.DAY_OF_MONTH);

        btn_kalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(InputKegiatanActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                            kalender.set(year, month, day);
                            str_tanggal = sdf.format(kalender.getTime());
                            btn_Kalender_click = true;
                            tanggalChanged();
                            tv_tanggal.setText(str_tanggal);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
            }
        });

        tv_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(InputKegiatanActivity.this, new DatePickerDialog.OnDateSetListener() {
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
    }

    public void uploadDokumentasi() {

        btn_uploadfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
            }
        });
    }

    public void insertData() {
        str_kegiatan = kegiatan.getText().toString();
        str_tindakLanjut = tindak_lanjut.getText().toString();
        str_hasil = hasil.getText().toString();
        str_persetujuan = persetujuan.getText().toString();

        agenda = new Agenda(str_tanggal, str_kegiatan, str_tindakLanjut, str_hasil, str_persetujuan, getAgenda.getJalan(), getAgenda.getKecamatan(), getAgenda.getKota(), getAgenda.getLokasiKM());
        db.insert_data(agenda, latLng);
        id_dokumentasi = Integer.parseInt(db.get_agendaID(agenda.getKegiatan(), agenda.getLokasiKM()));
        saveImageInDB();
    }

    public boolean saveImageInDB() {
        try {
            for (int i = 0; i < mArrayUri.size(); i++) {
                InputStream iStream = getContentResolver().openInputStream(mArrayUri.get(i));
                byte[] inputData = BitmapUtils.getBytes(iStream);
                db.insert_dokumentasi(id_dokumentasi, inputData);
            }
            db.close();
            return true;
        } catch (IOException ioe) {
            db.close();
            return false;
        }
    }

    public void simpanKegiatan() {
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                latLngList = db.get_latlng();
                Intent intent = new Intent(InputKegiatanActivity.this, MapsActivity.class);
                intent.putExtra("Latlng", latLngList);
                startActivity(intent);
            }
        });
    }
}