package com.lmo.fadergsn2.ui.subTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SubTaskViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    

    public LiveData<String> getText() {
        return mText;
    }
}