package com.example.driverwiki;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.example.driverwiki.interfaces.FormInterface;
import com.example.driverwiki.models.DriverEntity;
import com.example.driverwiki.presenters.FormPresenter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;


public class FormActivity extends AppCompatActivity implements FormInterface.View {

    private Context myContext;
    private FormInterface.Presenter presenter;
    public final Calendar c = Calendar.getInstance();
    DriverEntity d = new DriverEntity();
    private static final int REQUEST_CAPTURE_IMAGE = 200;
    private static final int REQUEST_SELECT_IMAGE = 201;
    private Uri uri;
    final private int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;

    private ConstraintLayout constraintLayoutFormActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        myContext = this;
        presenter = new FormPresenter(this);
        String id = getIntent().getStringExtra("id");




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Creación de piloto");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            Log.d("SobreNosotros", "Error al cargar toolbar");
        }

        ImageView imageView = findViewById(R.id.imageView2);






        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String[] opciones = {"Activo", "Retirado"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones));
        String spinnertext = spinner.getSelectedItem().toString();

        EditText readdate = (EditText) findViewById(R.id.date);
        readdate.setFocusable(false);




        TextInputEditText name = findViewById(R.id.getname);
        TextInputLayout tilname = findViewById(R.id.name);

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(d.setName(name.getText().toString()) == 0){
                        tilname.setError(null);
                        tilname.setBoxStrokeColor(Color.GREEN);
                    }else{
                        tilname.setError(presenter.getError(d.setName(name.getText().toString())));
                        tilname.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                    }
                }
            }
        });




        TextInputEditText gps = findViewById(R.id.getgps);
        TextInputLayout tilgps = findViewById(R.id.gps);
        gps.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(d.setGps(gps.getText().toString()) == 0){
                        tilgps.setError(null);
                        tilgps.setBoxStrokeColor(Color.GREEN);
                    }else{
                        tilgps.setError(presenter.getError(d.setGps(gps.getText().toString())));
                        tilgps.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                    }
                }
            }
        });


        TextInputEditText victories = findViewById(R.id.getvictories);
        TextInputLayout tilvictories = findViewById(R.id.victories);
        victories.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(d.setVictories(victories.getText().toString()) == 0){
                        tilvictories.setError(null);
                        tilvictories.setBoxStrokeColor(Color.GREEN);
                    }else{
                        tilvictories.setError(presenter.getError(d.setGps(gps.getText().toString())));
                        tilvictories.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                    }
                }
            }
        });

        ImageButton datepicker = findViewById(R.id.getdate);
        EditText et = findViewById(R.id.date);
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDate(et);
            }
        });





        Button button = (Button) findViewById(R.id.delete);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickDeleteButton();

            }
        });


        constraintLayoutFormActivity = findViewById(R.id.FORMCTL);

        ImageView buttonGallery = (ImageView) findViewById(R.id.imageView2);


        buttonGallery = (ImageView) findViewById(R.id.imageView2);

        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickImage();
            }
        });

        deleteIMG();




        if (id != null){
            DriverEntity d2 = presenter.getbyId(id);
            d.setId(id);
            name.setText(d2.getName());
            gps.setText(d2.getGps());
            victories.setText(d2.getVictories());
            et.setText(d2.getDate());
            byte[] decodedString = Base64.decode(d.getPhoto(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            buttonGallery.setImageBitmap(decodedByte);

        }






        Button buttonsave = (Button) findViewById(R.id.save);
        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                d.setName(name.getText().toString());
                d.setDate(et.getText().toString());
                d.setGps(gps.getText().toString());
                d.setVictories(victories.getText().toString());
                if(imageView!=null&&imageView.getDrawable()!=null){
                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    if(bitmap!=null){
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        String fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        d.setPhoto(fotoEnBase64);
                    }
                }

                presenter.onClickSaveButton(d);
                AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
                builder.setMessage("Piloto creado");
                builder.show();
            }
        });








    }



    public void getDate(EditText et) {
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int year = c.get(Calendar.YEAR);
        DatePickerDialog dategetter = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int actualmonth = month + 1;
                String dayform = (dayOfMonth < 10) ? 0 + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                String monthform = (actualmonth < 10) ? 0 + String.valueOf(actualmonth) : String.valueOf(actualmonth);
                et.setText(dayform + "/" + monthform + "/" + year);


            }
        }, year, month, day);
        dategetter.show();
        d.setDate(et.getText().toString());
    }

    @Override
    public void CloseFormActivity() {
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void alertDelete() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("¿Eliminar formulario?");
            builder.setMessage("Perderás toda la información escrita");

            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CloseFormActivity();
                }
            });
            builder.setNegativeButton("Cancelar", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formsearch, menu);
        return true;
    }

    public void deleteIMG(){
        Button deletephoto = (Button) findViewById(R.id.deletephoto);
        deletephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageCharged = findViewById(R.id.imageView2);
                imageCharged.setImageBitmap(null);
            }
        });
    }

    @Override
    public void IntentChooser(){
        ActivityCompat.requestPermissions(FormActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
    }


    @Override
    public void selectPicture(){
        // Se le pide al sistema una imagen del dispositivo
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, getResources().getString(R.string.choose_picture)),
                REQUEST_SELECT_IMAGE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case (REQUEST_CAPTURE_IMAGE):
                if (resultCode == Activity.RESULT_OK) {
                    // Se carga la imagen desde un objeto URI al imageView
                    ImageView imageView = findViewById(R.id.imageView2);
                    imageView.setImageURI(uri);

                    // Se le envía un broadcast a la Galería para que se actualice
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(uri);
                    sendBroadcast(mediaScanIntent);

                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // Se borra el archivo temporal
                    File file = new File(uri.getPath());
                    file.delete();
                }
                break;

            case (REQUEST_SELECT_IMAGE):
                if (resultCode == Activity.RESULT_OK) {
                    // Se carga la imagen desde un objeto Bitmap
                    Uri selectedImage = data.getData();
                    String selectedPath = selectedImage.getPath();

                    if (selectedPath != null) {
                        // Se leen los bytes de la imagen
                        InputStream imageStream = null;
                        try {
                            imageStream = getContentResolver().openInputStream(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        // Se transformam los bytes de la imagen a un Bitmap
                        Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                        // Se carga el Bitmap en el ImageView
                        Bitmap imageScaled = Bitmap.createScaledBitmap(bmp, 200, 200, false);
                        ImageView imageView = findViewById(R.id.imageView2);
                        imageView.setImageBitmap(imageScaled);
                    }
                }
                break;
        }
    }


    @Override
    public void showError(){
        Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.write_permission_denied), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.PermissionGranted();
                } else {
                    presenter.PermissionDenied();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    }


