package com.example.jasamarga3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DaftarKegiatanAdapter extends RecyclerView.Adapter<DaftarKegiatanAdapter.ViewHolder> {

    private final ArrayList<Agenda> agendaArrayList;
    private final Menu menu;
    private final Context context;

    public DaftarKegiatanAdapter(ArrayList<Agenda> agendaArrayList, Menu menu, Context context) {
        this.agendaArrayList = agendaArrayList;
        this.menu = menu;
        this.context = context;
    }

    @NonNull
    @Override
    public DaftarKegiatanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kegiatan, parent, false);
        return new DaftarKegiatanAdapter.ViewHolder(view, menu);
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarKegiatanAdapter.ViewHolder holder, int position) {
        Agenda agenda = agendaArrayList.get(position);
        holder.judul_kegiatan.setText(agenda.getKegiatan());
    }

    @Override
    public int getItemCount() {
        return agendaArrayList.size();
    }

    public interface Menu {
        void detail_agenda(Agenda agenda);

        void edit_agenda(Agenda agenda);

        void hapus_agenda(Agenda agenda);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView judul_kegiatan, tombol_menu;
        private final Menu menu;

        public ViewHolder(@NonNull View itemView, Menu menu) {
            super(itemView);

            judul_kegiatan = itemView.findViewById(R.id.tv_listKegiatan_judulAgenda);
            tombol_menu = itemView.findViewById(R.id.tv_listKegiatan_menu);
            tombol_menu.setOnClickListener(this);
            this.menu = menu;
        }

        @Override
        public void onClick(View view) {
            PopupMenu popup = new PopupMenu(context, tombol_menu);
            popup.inflate(R.menu.options_menu);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_details:
                            menu.detail_agenda(agendaArrayList.get(getAdapterPosition()));
                            break;
                        case R.id.menu_edit:
                            menu.edit_agenda(agendaArrayList.get(getAdapterPosition()));
                            break;
                        case R.id.menu_delete:
                            menu.hapus_agenda(agendaArrayList.get(getAdapterPosition()));
                            break;
                    }
                    return false;
                }
            });
            popup.show();
        }
    }
}
