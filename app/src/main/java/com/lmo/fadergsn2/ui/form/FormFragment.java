package com.lmo.fadergsn2.ui.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.lmo.fadergsn2.databinding.FragmentFromBinding;


public class FormFragment extends Fragment {

    private FormViewModel formViewModel;
    private FragmentFromBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        formViewModel =
                new ViewModelProvider(this).get(FormViewModel.class);

        binding = FragmentFromBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textForm;
        formViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}