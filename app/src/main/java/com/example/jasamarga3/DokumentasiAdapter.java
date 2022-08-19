package com.example.jasamarga3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DokumentasiAdapter extends RecyclerView.Adapter<DokumentasiAdapter.ViewHolder> {

    ArrayList<byte[]> imagesByte;
    Context context;

    public DokumentasiAdapter(ArrayList<byte[]> imagesByte, Context context) {
        this.imagesByte = imagesByte;
        this.context = context;
    }

    @NonNull
    @Override
    public DokumentasiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_dokumentasi, parent, false);
        return new DokumentasiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DokumentasiAdapter.ViewHolder holder, int position) {
        holder.foto_dokumentasi.setImageBitmap(BitmapUtils.getImage(imagesByte.get(position)));
    }

    @Override
    public int getItemCount() {
        return imagesByte.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView foto_dokumentasi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foto_dokumentasi = itemView.findViewById(R.id.iv_dokumentasi_foto);
        }
    }
}
