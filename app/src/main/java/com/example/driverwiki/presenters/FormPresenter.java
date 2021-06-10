
package com.example.driverwiki.presenters;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;


import com.example.driverwiki.MyApplication;
import com.example.driverwiki.interfaces.FormInterface;
import com.example.driverwiki.models.DriverEntity;
import com.example.driverwiki.models.DriverModel;


import android.app.Application;
import android.content.Context;


    public class FormPresenter implements FormInterface.Presenter{

        private FormInterface.View view;

        private DriverModel dm;

        public FormPresenter(FormInterface.View view) {
            this.view = view;
            this.dm = new DriverModel();

        }

        @Override
        public void onClickSaveButton(DriverEntity d) {
            if(dm.insert(d)){
            view.CloseFormActivity();
            }else{

            }
        }

        @Override
        public void onClickDeleteButton() {
            view.alertDelete();
        }

        @Override
        public String getError(int ErrorCode) {
            String message = "";
            switch (ErrorCode){
                case 1:
                    message = "Introduzca un nombre válido";
                    break;
                case 2:
                    message = "Introduzca un número positivo";
            }
            return message;
        }

        @Override
        public void AcceptDelete() {
            view.CloseFormActivity();
        }

        @Override
        public void PermissionGranted() {
            view.selectPicture();
        }

        @Override
        public void PermissionDenied() {
            view.showError();
        }

        @Override
        public DriverEntity getbyId(String id) {
            return dm.getbyId(id);
        }

        @Override
        public void onClickImage() {
            int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(MyApplication.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            Log.d("FormPresenter", "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);

            if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                // Permiso denegado
                // A partir de Marshmallow (6.0) se pide aceptar o rechazar el permiso en tiempo de ejecución
                // En las versiones anteriores no es posible hacerlo
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    view.IntentChooser();
                    // Una vez que se pide aceptar o rechazar el permiso se ejecuta el método "onRequestPermissionsResult" para manejar la respuesta
                    // Si el usuario marca "No preguntar más" no se volverá a mostrar este diálogo
                } else {
                    view.showError();
                }
            } else {
                // Permiso aceptado
                view.selectPicture();
            }
        }


    }


