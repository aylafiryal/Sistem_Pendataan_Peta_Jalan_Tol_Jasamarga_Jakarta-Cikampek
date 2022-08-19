package com.example.jasamarga3;

import java.io.Serializable;

public class Lokasi implements Serializable {

    String jalan, kecamatan, kota, lokasiKM;

    public Lokasi(String jalan, String kecamatan, String kota, String lokasiKM) {
        this.jalan = jalan;
        this.kecamatan = kecamatan;
        this.kota = kota;
        this.lokasiKM = lokasiKM;
    }

    public String getJalan() {
        return jalan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public String getKota() {
        return kota;
    }

    public String getLokasiKM() {
        return lokasiKM;
    }
}
