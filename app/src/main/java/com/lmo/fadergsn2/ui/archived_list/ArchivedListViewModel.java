package com.lmo.fadergsn2.ui.archived_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ArchivedListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ArchivedListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is archived lists");
    }

    public LiveData<String> getText() {
        return mText;
    }
}