package com.lmo.fadergsn2;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class TaskFirebase {
    private static final String COLLECTION = "tasks";
    private FirebaseFirestore base;
    private Task task;

    public TaskFirebase(){
        this.base = FirebaseFirestore.getInstance();
    }

    public TaskFirebase(Task object){ this.task = object; }

    public Boolean save(){
        final Boolean[] result = {Boolean.FALSE};
        Map<String,Object> task = new HashMap<>();
        task.put("id",this.task.getId());
        task.put("id",this.task.getUserId());
        task.put("id",this.task.getTitle());
        task.put("id",this.task.getDesc());
        task.put("id",this.task.isArchived());
        this.base.collection(this.COLLECTION)
            .add(task)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    result[0] = Boolean.TRUE;
                }
            }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("base","Erro adding document", e);
            }
        });
        return result[0];
    }

    public Boolean saveNewTask(Task task){
        task.setId(this.base.collection(this.COLLECTION).document().getId());
        Boolean result = Boolean.FALSE;
        this.base.collection(this.COLLECTION)
                .add(task);
        return result;
    }

}
