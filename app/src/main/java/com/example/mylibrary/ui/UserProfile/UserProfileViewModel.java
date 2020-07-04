package com.example.mylibrary.ui.UserProfile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserProfileViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UserProfileViewModel() {
        mText = new MutableLiveData<>();
       // mText.setValue("This is UserProfile fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}