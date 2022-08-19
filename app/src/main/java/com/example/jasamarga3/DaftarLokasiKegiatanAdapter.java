package com.example.jasamarga3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DaftarLokasiKegiatanAdapter extends RecyclerView.Adapter<DaftarLokasiKegiatanAdapter.ViewHolder> implements DaftarKegiatanAdapter.Menu {

    private final JasamargaDatabase db;
    private ArrayList<Agenda> agendaKategori = new ArrayList<>();
    private final ArrayList<Agenda> agendaArrayList;
    private ArrayList<Agenda> agendaKosong = new ArrayList<>();
    private final ArrayList<Lokasi> listLokasi;
    private final TambahKegiatan tambahKegiatan;
    private final Context context;

    public DaftarLokasiKegiatanAdapter(JasamargaDatabase db, ArrayList<Agenda> agendaArrayList, ArrayList<Lokasi> listLokasi, TambahKegiatan tambahKegiatan, Context context) {
        this.db = db;
        this.agendaArrayList = agendaArrayList;
        this.tambahKegiatan = tambahKegiatan;
        this.listLokasi = listLokasi;
        this.context = context;
    }


    @NonNull
    @Override
    public DaftarLokasiKegiatanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_lokasi, parent, false);
        return new ViewHolder(view, tambahKegiatan);
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarLokasiKegiatanAdapter.ViewHolder holder, int position) {
        holder.lokasi.setText(listLokasi.get(position).getLokasiKM());
        agendaKategori = db.read_data(listLokasi.get(position).getLokasiKM());
        DaftarKegiatanAdapter daftarKegiatanAdapter = new DaftarKegiatanAdapter(agendaKategori, this, context);
        holder.rv_kegiatan.setAdapter(daftarKegiatanAdapter);
    }

    @Override
    public int getItemCount() {
        return listLokasi.size();
    }

    @Override
    public void detail_agenda(Agenda agenda) {
        Intent intent = new Intent(context, DetailKegiatanActivity.class);
        intent.putExtra("agenda_curr", agenda);
        context.startActivity(intent);
    }

    @Override
    public void edit_agenda(Agenda agenda) {
        Intent intent = new Intent(context, EditKegiatanActivity.class);
        intent.putExtra("agenda_curr", agenda);
        context.startActivity(intent);
    }

    @Override
    public void hapus_agenda(Agenda agenda) {
        DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        db.delete_data(agenda.getLokasiKM(), agenda.getKegiatan());
                        notifyDataSetChanged();
                        agendaKosong = db.read_data(agenda.getLokasiKM());
                        if (agendaKosong.size() < 1) {
                            listLokasi.remove(agenda.getLokasiKM());
                            notifyDataSetChanged();
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Hapus agenda " + agenda.getKegiatan() + "?").setPositiveButton("Ya", dialog)
                .setNegativeButton("Batal", dialog).show();

    }

    public interface TambahKegiatan {
        void tambahKegiatan(Lokasi lokasi);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView lokasi;
        private final Button tambah_kegiatan;
        private final RecyclerView rv_kegiatan;
        private final TambahKegiatan tambahKegiatan;

        public ViewHolder(@NonNull View itemView, TambahKegiatan tambahKegiatan) {
            super(itemView);
            lokasi = itemView.findViewById(R.id.tv_listRiwayat_lokasiRiwayat);
            rv_kegiatan = itemView.findViewById(R.id.rv_daftarKegiatan);
            tambah_kegiatan = itemView.findViewById(R.id.btn_lokasi_tambahKegiatan);
            tambah_kegiatan.setOnClickListener(this);
            this.tambahKegiatan = tambahKegiatan;
        }

        @Override
        public void onClick(View view) {
            tambahKegiatan.tambahKegiatan(listLokasi.get(getAdapterPosition()));
        }
    }
}