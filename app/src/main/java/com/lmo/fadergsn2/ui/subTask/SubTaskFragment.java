package com.lmo.fadergsn2.ui.subTask;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lmo.fadergsn2.R;
import com.lmo.fadergsn2.Task;
import com.lmo.fadergsn2.User;
import com.lmo.fadergsn2.databinding.FragmentSubTaskBinding;

public class SubTaskFragment extends Fragment {
    private Bundle args;
    private String argString;
    private TextView fsttvMainTaskTitle;

    private SubTaskViewModel subTaskViewModel;
    private FragmentSubTaskBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        subTaskViewModel =
                new ViewModelProvider(this).get(SubTaskViewModel.class);

        binding = FragmentSubTaskBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        fsttvMainTaskTitle = view.findViewById(R.id.fsttvMainTaskTitle);
        args = getArguments();
        argString = args.get("task").toString();
        Task task = new Gson().fromJson(argString,Task.class);

        fsttvMainTaskTitle.setText(task.getTitle());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}