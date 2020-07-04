package com.example.mylibrary.ui.AdminHome;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdminHomeViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AdminHomeViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is slideshow  Home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
