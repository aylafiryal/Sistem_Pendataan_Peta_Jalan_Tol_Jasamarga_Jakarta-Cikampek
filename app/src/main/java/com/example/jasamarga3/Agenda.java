package com.example.jasamarga3;

import java.io.Serializable;
import java.util.ArrayList;

public class Agenda implements Serializable {

    private String tanggal, kegiatan, tindakLanjut, hasil, persetujuan, jalan, kecamatan, kota, lokasiKM;
    private final ArrayList<String> list_lokasi = new ArrayList<String>();
    private Boolean diff;

    public Agenda() {

    }

    public Agenda(String tanggal, String kegiatan, String tindakLanjut, String hasil, String persetujuan, String jalan, String kecamatan, String kota, String lokasiKM) {
        this.tanggal = tanggal;
        this.kegiatan = kegiatan;
        this.tindakLanjut = tindakLanjut;
        this.hasil = hasil;
        this.persetujuan = persetujuan;
        this.jalan = jalan;
        this.kecamatan = kecamatan;
        this.kota = kota;
        this.lokasiKM = lokasiKM;
    }

    public Agenda(String tanggal, String kegiatan, String tindakLanjut, String hasil, String persetujuan) {
        this.tanggal = tanggal;
        this.kegiatan = kegiatan;
        this.tindakLanjut = tindakLanjut;
        this.hasil = hasil;
        this.persetujuan = persetujuan;
    }

    public Agenda(String jalan, String kecamatan, String kota, String lokasiKM, boolean diff) {
        this.jalan = jalan;
        this.kecamatan = kecamatan;
        this.kota = kota;
        this.lokasiKM = lokasiKM;
        this.diff = diff;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(String kegiatan) {
        this.kegiatan = kegiatan;
    }

    public String getTindakLanjut() {
        return tindakLanjut;
    }

    public void setTindakLanjut(String tindakLanjut) {
        this.tindakLanjut = tindakLanjut;
    }

    public String getHasil() {
        return hasil;
    }

    public void setHasil(String hasil) {
        this.hasil = hasil;
    }

    public String getPersetujuan() {
        return persetujuan;
    }

    public void setPersetujuan(String persetujuan) {
        this.persetujuan = persetujuan;
    }

    public String getJalan() {
        return jalan;
    }

    public void setJalan(String jalan) {
        this.jalan = jalan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getLokasiKM() {
        return lokasiKM;
    }

    public void setLokasiKM(String lokasiKM) {
        this.lokasiKM = lokasiKM;
    }
}
