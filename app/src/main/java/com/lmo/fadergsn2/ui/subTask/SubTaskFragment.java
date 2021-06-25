package com.lmo.fadergsn2.ui.subTask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lmo.fadergsn2.databinding.FragmentSubTaskBinding;

public class SubTaskFragment extends Fragment {

    private SubTaskViewModel subTaskViewModel;
    private FragmentSubTaskBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        subTaskViewModel =
                new ViewModelProvider(this).get(SubTaskViewModel.class);

        binding = FragmentSubTaskBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}