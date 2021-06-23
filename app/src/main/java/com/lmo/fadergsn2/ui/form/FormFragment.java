package com.lmo.fadergsn2.ui.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.lmo.fadergsn2.Instance;
import com.lmo.fadergsn2.R;
import com.lmo.fadergsn2.Task;
import com.lmo.fadergsn2.TaskFirebase;
import com.lmo.fadergsn2.databinding.FragmentFromBinding;


public class FormFragment extends Fragment {
    private FragmentFromBinding binding;

    private EditText ffetTitle, ffetDesc;
    private Button ffBtnSend;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_from, container, false);

        EditText ffetTitle = (EditText) view.findViewById(R.id.ffetTitle);
        EditText ffetDesc = (EditText) view.findViewById(R.id.ffetDesc);

        Button ffBtnSend = (Button) view.findViewById(R.id.ffBtnSend);

        ffBtnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String title,desc;
                title = ffetTitle.toString().trim();
                desc = ffetDesc.toString().trim();
                Task task = new Task(null, Instance.getInstance().user.getId(),title,desc,Boolean.FALSE);
                TaskFirebase taskFirebase = new TaskFirebase(task);

                taskFirebase.save();

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}