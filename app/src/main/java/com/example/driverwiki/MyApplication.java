package com.example.driverwiki;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext =   getApplicationContext();

        // Inicializar la base de datos sólo una vez por aplicación
        Realm.init(sContext);

        // Aplicar configuración específica a la base de datos
        // name(): establecer el nombre del fichero de la base de datos
        // .directory(): establecer la ruta del fichero de la base de datos
        // .allowQueriesOnUiThread(): permitir consultas en el mismo hilo de la interfaz gráfica
        // .allowWritesOnUiThread(): permitir escrituras en el mismo hilo de la interfaz gráfica
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                //.name("demoandroid.realm")
                //.directory(getExternalFilesDirs(null)[1])
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();
        Realm.setDefaultConfiguration(config);

        // Comprobar la ruta de almacenamiento de la base de datos
        // La ruta por defecto es /data/data/[app]/files
        /*
        Realm realm = Realm.getDefaultInstance();
        Log.d("DemoAndroidRealm", "Path: " + realm.getPath());
        realm.close();
        */
    }

    public static Context getContext() {
        return sContext;
    }
}