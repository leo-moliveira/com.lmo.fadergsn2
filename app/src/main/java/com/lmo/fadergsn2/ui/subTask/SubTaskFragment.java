package com.lmo.fadergsn2.ui.subTask;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lmo.fadergsn2.AdapterSubTask;
import com.lmo.fadergsn2.AdapterTask;
import com.lmo.fadergsn2.MainActivity;
import com.lmo.fadergsn2.R;
import com.lmo.fadergsn2.SubTask;
import com.lmo.fadergsn2.Task;
import com.lmo.fadergsn2.User;
import com.lmo.fadergsn2.databinding.FragmentSubTaskBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SubTaskFragment extends Fragment {
    private Bundle args;
    private String argString;
    private TextView fsttvMainTaskTitle, fsttvNonSubTasks;
    private ListView fstlvSubTasks;
    private SubTaskViewModel subTaskViewModel;
    private FragmentSubTaskBinding binding;
    private List<SubTask> listOfSubTasks = new ArrayList<>();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();;
    private AdapterSubTask adapterSubTask;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        subTaskViewModel =
                new ViewModelProvider(this).get(SubTaskViewModel.class);

        binding = FragmentSubTaskBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        fsttvMainTaskTitle = view.findViewById(R.id.fsttvMainTaskTitle);
        fstlvSubTasks = view.findViewById(R.id.fstlvSubTasks);
        fsttvNonSubTasks = view.findViewById(R.id.fsttvNonSubTasks);

        args = getArguments();
        argString = args.get("task").toString();
        Task mainTask = new Gson().fromJson(argString,Task.class);
        fsttvMainTaskTitle.setText(mainTask.getTitle());

        this.firebaseFirestore.collection("subtasks")
                .whereEqualTo("userId",mainTask.getUserId())
                .whereEqualTo("completed",Boolean.FALSE)
                .whereEqualTo("taskId",mainTask.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listOfSubTasks.add(document.toObject(SubTask.class));
                                Collections.sort(listOfSubTasks, new Comparator<SubTask>() {
                                    @Override
                                    public int compare(SubTask o1, SubTask o2) {
                                        return o1.getCreatedAt().compareTo(o2.getCreatedAt());
                                    }
                                });
                            }
                                loadSubTasks();
                        }else{
                            fsttvNonSubTasks.setVisibility(View.VISIBLE);
                        }
                    }
                });

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_main);
                Bundle argsOut = new Bundle();
                argsOut.putString("action","newsubtask");
                argsOut.putString("task",args.get("task").toString());
                navController.navigate(R.id.nav_form,argsOut);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void loadSubTasks(){
        fsttvNonSubTasks.setVisibility(View.INVISIBLE);
        adapterSubTask = new AdapterSubTask(this.getContext(),this.listOfSubTasks);
        fstlvSubTasks.setAdapter(adapterSubTask);
    }
}