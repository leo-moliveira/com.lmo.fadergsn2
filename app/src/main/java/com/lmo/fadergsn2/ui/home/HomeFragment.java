package com.lmo.fadergsn2.ui.home;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lmo.fadergsn2.R;
import com.lmo.fadergsn2.Task;
import com.lmo.fadergsn2.User;
import com.lmo.fadergsn2.databinding.FragmentHomeBinding;
import com.lmo.fadergsn2.ui.form.FormFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private TextView fhtvTaskTitle;
    private TextView fhtvTaskDesc;

    private User userData;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    List<Task> listOfTasks;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        fhtvTaskTitle = view.findViewById(R.id.fhtvTaskTitle);
        fhtvTaskDesc = view.findViewById(R.id.fhtvTaskDesc);
        listOfTasks = new ArrayList<>();
        this.userData = FormFragment.getUserData(this.getContext());
        this.firebaseFirestore.collection("tasks")
                .whereEqualTo("userId",userData.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listOfTasks.add(document.toObject(Task.class));
                                Collections.sort(listOfTasks, new Comparator<Task>() {
                                    @Override
                                    public int compare(Task o1, Task o2) {
                                        return o1.getCreatedAt().compareTo(o2.getCreatedAt());
                                    }
                                });
                            }
                        }else{
                            fhtvTaskTitle.setText(view.getResources().getString(R.string.noTasks));
                        }
                    }
                });

        /*
        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                fhtvTaskTitle.setText(view.getResources().getString(R.string.noTasks));
            }
        }
         */
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}