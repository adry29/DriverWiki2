package com.example.driverwiki.interfaces;

import com.example.driverwiki.models.DriverEntity;

import java.util.ArrayList;

public interface ListInterface {
    public interface View{
        void StartFormActivity();
        void StartFormActivity(String id);
        void StartAboutActivity();
        void StartSearchActivity();

    }
    public interface Presenter{
        void onClickFloatingButton();
        void onClickAboutButton();
        void onClickSearchButton();
        public void onClickItem(String id);
        ArrayList<DriverEntity> getAllSummarize();
        public DriverEntity getbyid (String id);
        public void delete (DriverEntity d);
        ArrayList<DriverEntity> getFilteredDrivers(String name, String date);

    }
}
