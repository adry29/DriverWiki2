package com.example.driverwiki.presenters;

import com.example.driverwiki.interfaces.FormInterface;
import com.example.driverwiki.interfaces.SearchInterface;
import com.example.driverwiki.models.DriverModel;


import java.util.ArrayList;

public class SearchPresenter implements SearchInterface.Presenter {
    private SearchInterface.View view;
    private DriverModel dm;
    public SearchPresenter(SearchInterface.View view){this.view=view;
    this.dm = new DriverModel();}
    @Override
    public void onClickSearchbutton() {
        view.StartListActivity();
    }


}
