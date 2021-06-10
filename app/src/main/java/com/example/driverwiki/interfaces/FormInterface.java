package com.example.driverwiki.interfaces;


import com.example.driverwiki.models.DriverEntity;

public interface FormInterface {
    public interface View{
        void CloseFormActivity();
        void alertDelete();
        void selectPicture();
        void showError();
        void IntentChooser();

    }
    public interface Presenter{

        void onClickSaveButton(DriverEntity d);
        void onClickDeleteButton();
        String getError(int ErrorCode);
        void AcceptDelete();
        void onClickImage();
        void PermissionGranted();
        void PermissionDenied();
        DriverEntity getbyId(String id);

    }
}
