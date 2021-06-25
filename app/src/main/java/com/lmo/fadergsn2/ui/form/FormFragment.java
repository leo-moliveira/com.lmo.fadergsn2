package com.lmo.fadergsn2.ui.form;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.type.DateTime;
import com.lmo.fadergsn2.Instance;
import com.lmo.fadergsn2.MainActivity;
import com.lmo.fadergsn2.R;
import com.lmo.fadergsn2.TaskFirebase;
import com.lmo.fadergsn2.User;
import com.lmo.fadergsn2.databinding.FragmentFromBinding;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class FormFragment extends Fragment {
    private FragmentFromBinding binding;

    private EditText ffetTitle, ffetDesc;
    private Button ffBtnSend;
    private User userData;

    private FirebaseFirestore base = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_from, container, false);

        EditText ffetTitle = (EditText) view.findViewById(R.id.ffetTitle);
        EditText ffetDesc = (EditText) view.findViewById(R.id.ffetDesc);

        Button ffBtnSend = (Button) view.findViewById(R.id.ffBtnSend);

        userData = getUserData(this.getContext());

        ffBtnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String title,desc;
                title = ffetTitle.getText().toString().trim();
                desc = ffetDesc.getText().toString().trim();

                DocumentReference doc = base.collection("tasks").document();
                String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                Map<String,Object> task = new HashMap<>();
                task.put("id",doc.getId());
                task.put("userId",userData.getId());
                task.put("title",title);
                task.put("desc",desc);
                task.put("createdAt", timeStamp);
                task.put("archived",Boolean.FALSE);
                base.collection("tasks").add(task).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                        Toast.makeText(v.getContext(),v.getResources().getString(R.string.taskCreated),Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(v.getContext(),v.getResources().getString(R.string.failedTaskCreate),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static User getUserData(Context context){
        Gson gson = new Gson();
        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn = context.openFileInput ( "userData.json" ) ;
            InputStreamReader isr = new InputStreamReader ( fIn ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            String readString = buffreader.readLine ( ) ;
            while ( readString != null ) {
                datax.append(readString);
                readString = buffreader.readLine ( ) ;
            }

            isr.close ( ) ;
        } catch ( IOException ioe ) {
            ioe.printStackTrace ( ) ;
        }
        String s = datax.toString();
        User jsonNew = gson.fromJson(s,User.class);
        return jsonNew;
    }
}