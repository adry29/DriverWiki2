package com.example.driverwiki.models;


import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

    public class DriverModel {
        public ArrayList<DriverEntity> getAllSummarize(){
            Realm realm = Realm.getDefaultInstance();
            RealmResults<DriverEntity> result = realm.where(DriverEntity.class).findAll();
            ArrayList<DriverEntity> driverList = new ArrayList<>();
            driverList.addAll(realm.copyFromRealm(result));
            realm.close();
            ArrayList<DriverEntity> driverListSummarize = new ArrayList<>();
            for(DriverEntity d : driverList){
                driverListSummarize.add(new DriverEntity(d.getId(), d.getName(), d.getGps(), d.getVictories(), d.getDate(), d.isActive(), d.isWorldchampion(), d.getPhoto()));
            }
            return driverListSummarize;
        }

        public boolean insert(DriverEntity d){
            boolean result = true;
            Realm realm = Realm.getDefaultInstance();
            if(d.getId().equals("")){
                realm.executeTransaction(r -> {
                    d.setId(UUID.randomUUID().toString());
                    realm.copyToRealm(d);

                });
                realm.close();
            }else{
                realm.executeTransaction(r -> {
                    realm.copyToRealmOrUpdate(d);
                });
                realm.close();
            }
            Log.d("DemoRealm", "Path: " + realm.getPath());
            realm.close();
            return result;
        }

        public DriverEntity getbyId(String id){
            Realm realm = Realm.getDefaultInstance();
            DriverEntity found = realm.where(DriverEntity.class).equalTo("id", id).findFirst();
            DriverEntity result;
            result = realm.copyFromRealm(found);
            realm.close();
            return result;
        }

        public boolean deleteDriver(DriverEntity d){
            boolean result;
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(r -> {
                DriverEntity found = realm.where(DriverEntity.class).equalTo("id", d.getId()).findFirst();
                if(found != null){
                    found.deleteFromRealm();
                }


            });
            result = true;
            realm.close();
            return result;
        }

        public ArrayList<DriverEntity> getWithFilterBoth(String name, String date){
            Realm realm = Realm.getDefaultInstance();

            RealmResults<DriverEntity> result;

            if(date==null || date.equals("Seleccione una fecha")){
                result = realm.where(DriverEntity.class).contains("name", name)
                        .findAll();
            }else{
                result = realm.where(DriverEntity.class).contains("name", name)
                        .equalTo("date", date)
                        .findAll();
            }

            Log.d("Realm find items: ", "" + result.size());

            ArrayList<DriverEntity> driverList = new ArrayList<>();
            driverList.addAll(realm.copyFromRealm(result));

            realm.close();

            ArrayList<DriverEntity> listSummarize = new ArrayList<>();

            for(DriverEntity driver: driverList){
                DriverEntity newdriver = new DriverEntity();
                newdriver.setId(driver.getId());
                newdriver.setName(driver.getName());
                newdriver.setGps(driver.getGps());
                newdriver.setVictories(driver.getVictories());
                newdriver.setDate(driver.getDate());
                newdriver.setPhoto(driver.getPhoto());
                listSummarize.add(newdriver);
            }

            return listSummarize;
        }


    }


