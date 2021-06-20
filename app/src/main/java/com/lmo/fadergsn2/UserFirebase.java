package com.lmo.fadergsn2;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UserFirebase {
    private static final String COLLECTION = "users";
    private FirebaseFirestore base;
    private User user;

    public UserFirebase(){}

    public UserFirebase(User Object){
        this.user = Object;
    }

    public void save(){
        base = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("id",this.user.getId());
        user.put("name",this.user.getName());
        user.put("email",this.user.getEmail());

        base.collection(this.COLLECTION)
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("base", "document User saved id:" + documentReference);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("base","erro adding document", e);
            }
        });
    }
    public User findByUserId(String id){
        base = FirebaseFirestore.getInstance();
        base.collection(this.COLLECTION).whereEqualTo("id",id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                user = document.toObject(User.class);
                                Log.d("teste", user.getClass() + " => " + user.getId() + " - " + user.getName() + " - " + user.getEmail());
                            }
                        } else {
                            user = null;
                            Log.d("teste", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return this.user;
    }
}
