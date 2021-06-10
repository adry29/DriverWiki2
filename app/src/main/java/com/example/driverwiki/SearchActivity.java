package com.example.driverwiki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.driverwiki.presenters.SearchPresenter;
import com.example.driverwiki.interfaces.SearchInterface;


import java.util.Calendar;

public class SearchActivity extends AppCompatActivity implements SearchInterface.View {
    public final Calendar c = Calendar.getInstance();
    private SearchInterface.Presenter presenter;
    EditText etn;
    TextView etd;
    Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        presenter = new SearchPresenter(this);
        setContentView(R.layout.activity_search);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Filtrar búsqueda");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            Log.d("SobreNosotros", "Error al cargar toolbar");
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinnerfilter);
        String[] opciones = {"Activo"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones));

        etn = (EditText) findViewById(R.id.gettext);

        myContext = this;
        etd = (TextView) findViewById(R.id.textView3);
        etd.setText("Seleccione una fecha");
        etd.setFocusable(false);

        ImageButton datepicker = findViewById(R.id.getdatefilter);
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDate(etd);
            }
        });

        Button button = findViewById(R.id.searchbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickSearchbutton();

            }
        });

    }




    public String getDate(TextView et) {
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
        return et.getText().toString();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formsearch, menu);
        return true;
    }

    @Override
    public void StartListActivity() {
        Intent i = getIntent();
        i.putExtra("name", etn.getText().toString());
        i.putExtra("date", etd.getText().toString());
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onPause() {
        ;
        super.onPause();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onRestart() {

        super.onRestart();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}