package com.example.driverwiki.presenters;

import com.example.driverwiki.interfaces.ListInterface;
import com.example.driverwiki.models.DriverEntity;
import com.example.driverwiki.models.DriverModel;

import java.util.ArrayList;

public class ListPresenter implements ListInterface.Presenter {

    private ListInterface.View view;
    private DriverModel dm;

    public ListPresenter(ListInterface.View view) {
        this.view = view;
        this.dm = new DriverModel();
    }

    @Override
    public void onClickFloatingButton() {
        view.StartFormActivity();
    }

    @Override
    public void onClickAboutButton() {
        view.StartAboutActivity();
    }

    @Override
    public void onClickSearchButton() {
        view.StartSearchActivity();
    }

    @Override
    public void onClickItem(String id) {
        view.StartFormActivity(id);
    }

    @Override
    public ArrayList<DriverEntity> getAllSummarize() {
        return dm.getAllSummarize();
    }

    @Override
    public DriverEntity getbyid(String id) {
        return dm.getbyId(id);
    }

    @Override
    public void delete(DriverEntity d) {
        dm.deleteDriver(d);
    }

    @Override
    public ArrayList<DriverEntity> getFilteredDrivers(String name, String date) {

            return dm.getWithFilterBoth(name, date);
    }
}
