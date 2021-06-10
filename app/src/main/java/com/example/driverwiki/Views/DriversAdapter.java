package com.example.driverwiki.Views;


import android.os.Build;
import android.view.ViewGroup;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.driverwiki.R;

import com.example.driverwiki.models.DriverEntity;

import java.util.ArrayList;


public class DriversAdapter extends RecyclerView.Adapter<DriversAdapter.DriversViewHolder> implements View.OnClickListener {

    private ArrayList<DriverEntity> drivers;
    private View.OnClickListener listener;



    // Clase interna:
    // Se implementa el ViewHolder que se encargará
    // de almacenar la vista del elemento y sus datos
    public static class DriversViewHolder
            extends RecyclerView.ViewHolder {

        private TextView TextView_name;
        private TextView TextView_gps;
        private ImageView ImageView_photo;

        public DriversViewHolder(View itemView) {
            super(itemView);
            TextView_name = (TextView) itemView.findViewById(R.id.driverName);
            TextView_gps = (TextView) itemView.findViewById(R.id.driverGPs);
            ImageView_photo = (ImageView) itemView.findViewById(R.id.photo);
        }


        public void DriverBind(DriverEntity item) {
            byte[] decodedString = Base64.decode(item.getPhoto(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ImageView_photo.setImageBitmap(decodedByte);

            TextView_name.setText(item.getName());
            TextView_gps.setText(item.getGps());
        }
    }

    // Contruye el objeto adaptador recibiendo la lista de datos
    public DriversAdapter(@NonNull ArrayList<DriverEntity> items) {
        this.drivers = items;
    }

    // Se encarga de crear los nuevos objetos ViewHolder necesarios
    // para los elementos de la colección.
    // Infla la vista del layout, crea y devuelve el objeto ViewHolder
    @Override
    public DriversViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.driver_row, parent, false);
        row.setOnClickListener(this);

        DriversViewHolder avh = new DriversViewHolder(row);
        return avh;
    }

    // Se encarga de actualizar los datos de un ViewHolder ya existente.
    @Override
    public void onBindViewHolder(DriversViewHolder viewHolder, int position) {
        DriverEntity item = drivers.get(position);
        viewHolder.DriverBind(item);
    }

    // Indica el número de elementos de la colección de datos.
    @Override
    public int getItemCount() {
        return drivers.size();
    }

    // Asigna un listener al elemento
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }
}