package com.example.jasamarga3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class JasamargaDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "JasamargaDB6.db";
    private static final int DATABASE_VERSION = 1;

    public JasamargaDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Kegiatan(id INTEGER PRIMARY KEY, jalan TEXT, kecamatan TEXT, kota TEXT, lokasiKM TEXT, tanggal TEXT, kegiatan TEXT, tindak_lanjut TEXT, hasil TEXT, persetujuan TEXT, lat TEXT, lng TEXT)");
        sqLiteDatabase.execSQL("create table Dokumentasi(id INTEGER, images BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists Kegiatan");
        onCreate(sqLiteDatabase);
    }

    public void insert_data(Agenda agenda, LatLng latLng) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("jalan", agenda.getJalan());
        contentValues.put("kecamatan", agenda.getKecamatan());
        contentValues.put("kota", agenda.getKota());
        contentValues.put("lokasiKM", agenda.getLokasiKM());
        contentValues.put("tanggal", agenda.getTanggal());
        contentValues.put("kegiatan", agenda.getKegiatan());
        contentValues.put("tindak_lanjut", agenda.getTindakLanjut());
        contentValues.put("hasil", agenda.getHasil());
        contentValues.put("persetujuan", agenda.getPersetujuan());
        contentValues.put("lat", latLng.latitude);
        contentValues.put("lng", latLng.longitude);
        long data = db.insert("Kegiatan", null, contentValues);
    }

    public LatLng get_latlng(String lokasiKM) {
        SQLiteDatabase db = this.getReadableDatabase();
        LatLng latLng = null;
        Cursor cursor = db.rawQuery("SELECT lat, lng FROM Kegiatan WHERE lokasiKM = ?", new String[]{lokasiKM});
        if (cursor.moveToFirst()) {
            do {
                double lat = Double.valueOf(cursor.getString(0));
                double lng = Double.valueOf(cursor.getString(1));
                latLng = new LatLng(lat, lng);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return latLng;
    }

    public ArrayList<LatLng> get_latlng() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<LatLng> latLngList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT lat, lng FROM Kegiatan", null);
        if (cursor.moveToFirst()) {
            do {
                double lat = Double.valueOf(cursor.getString(0));
                double lng = Double.valueOf(cursor.getString(1));
                latLngList.add(new LatLng(lat, lng));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return latLngList;
    }

    public ArrayList<Agenda> read_data() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Kegiatan", null);
        ArrayList<Agenda> AgendaList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                AgendaList.add(new Agenda(cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return AgendaList;
    }

    public ArrayList<Agenda> read_data(String lokasiKM) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Kegiatan WHERE lokasiKM = ?", new String[]{lokasiKM});
        ArrayList<Agenda> AgendaList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                AgendaList.add(new Agenda(cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return AgendaList;
    }

//    public ArrayList<String> read_lokasi(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT DISTINCT lokasiKM FROM Kegiatan", null);
//        ArrayList<String> listLokasi = new ArrayList<>();
//        if(cursor.moveToFirst()){
//            do {
//                listLokasi.add(cursor.getString(0));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return listLokasi;
//    }

    public ArrayList<Lokasi> read_lokasi() {
        SQLiteDatabase db = this.getReadableDatabase();
        //jalan TEXT, kecamatan TEXT, kota TEXT, lokasiKM TEXT
        Cursor cursor = db.rawQuery("SELECT DISTINCT jalan, kecamatan, kota, lokasiKM FROM Kegiatan", null);
        ArrayList<Lokasi> listLokasi = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                listLokasi.add(new Lokasi(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listLokasi;
    }


    public String get_agendaID(String kegiatan, String lokasiKM) {
        SQLiteDatabase db = this.getReadableDatabase();
        String id = "";
        Cursor cursor = db.rawQuery("SELECT id FROM Kegiatan WHERE kegiatan = ? AND lokasiKM = ?", new String[]{kegiatan, lokasiKM});
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return id;
    }

    public void update_data(String id, Agenda agenda) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("jalan", agenda.getJalan());
        contentValues.put("kecamatan", agenda.getKecamatan());
        contentValues.put("kota", agenda.getKota());
        contentValues.put("lokasiKM", agenda.getLokasiKM());
        contentValues.put("tanggal", agenda.getTanggal());
        contentValues.put("kegiatan", agenda.getKegiatan());
        contentValues.put("tindak_lanjut", agenda.getTindakLanjut());
        contentValues.put("hasil", agenda.getHasil());
        contentValues.put("persetujuan", agenda.getPersetujuan());
        db.update("Kegiatan", contentValues, "id = ?", new String[]{id});
    }

    public void delete_data(String lokasiKM, String kegiatan) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Kegiatan", "lokasiKM = ? AND kegiatan = ?", new String[]{lokasiKM, kegiatan});
        db.close();
    }

    public void insert_dokumentasi(int id, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("images", image);
        long data = db.insert("Dokumentasi", null, contentValues);
    }

    public ArrayList<byte[]> read_dokumentasi(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT images FROM Dokumentasi WHERE id = ?", new String[]{String.valueOf(id)});
        ArrayList<byte[]> images = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                images.add(cursor.getBlob(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return images;
    }
}