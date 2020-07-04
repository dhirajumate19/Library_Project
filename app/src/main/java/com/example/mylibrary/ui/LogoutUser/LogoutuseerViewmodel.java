package com.example.mylibrary.ui.LogoutUser;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LogoutuseerViewmodel extends ViewModel {
    private MutableLiveData<String> mText;

    public LogoutuseerViewmodel() {
        mText = new MutableLiveData<>();
       // mText.setValue("This is Logoutuser fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
