package com.lmo.fadergsn2.ui.form;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FormViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FormViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This place for FORM");
    }

    public LiveData<String> getText() {
        return mText;
    }
}