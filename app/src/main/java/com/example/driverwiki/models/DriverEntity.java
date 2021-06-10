package com.example.driverwiki.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DriverEntity extends RealmObject {
    @PrimaryKey
    private String id;

    private String name;

    private String gps;

    private String victories;

    private boolean isActive;

    private boolean worldchampion;

    private String date;

    private String photo;


    public DriverEntity (String id, String name, String gps, String victories, String date, boolean active, boolean worldchampion, String photo){
        this.id = id;
        this.name = name;
        this.gps = gps;
        this.victories = victories;
        this.isActive = active;
        this.worldchampion = worldchampion;
        this.date = date;
        this.photo = photo;
    }

    public DriverEntity (){
        this.id = "";
        this.name = "";
        this.gps = "0";
        this.victories = "0";
        setActive(false);
        setWorldchampion(false);
        setDate("");
        setPhoto("");
    }

    public String getName(){
        return name;
    }

    public int setName(String name) {
        if(name.length()>2 && name.startsWith(String.valueOf(name.toUpperCase().charAt(0)))){
            this.name = name;
            return 0;
        }else{
            return 1;
        }
    }

    public String getGps(){
        return gps;
    }

    public int setGps(String gps) {
        if(gps.matches("[+-]?\\d*(\\.\\d+)?")){
            this.gps = gps;
            return 0;
        }else {
            return 2;
        }
    }

    public String getVictories(){
        return victories;
    }

    public int setVictories(String victories) {
        if(victories.matches("[+-]?\\d*(\\.\\d+)?")){
            this.victories = victories;
            return 0;
        }else {
            return 2;
        }
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setPhoto(String photo){this.photo = photo;}
    public String getPhoto(){return photo;}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isWorldchampion() {
        return worldchampion;
    }

    public void setWorldchampion(boolean worldchampion) {
        this.worldchampion = worldchampion;
    }

    public String getDate() {
        return date;
    }
}
