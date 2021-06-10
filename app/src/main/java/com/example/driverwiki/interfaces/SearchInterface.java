package com.example.driverwiki.interfaces;

import java.util.ArrayList;

public interface SearchInterface {
    public interface View{
        void StartListActivity();
    }
    public interface Presenter{
        void onClickSearchbutton();

    }
}