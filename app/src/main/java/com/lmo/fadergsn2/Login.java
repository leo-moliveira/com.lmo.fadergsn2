package com.lmo.fadergsn2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Login extends AppCompatActivity {
    EditText laetPassword, laetEmail;
    TextView laBtnReg,laForgotPass;
    Button laBtnLogin;
    ProgressBar laProgressBar;

    User user;

    FirebaseAuth firebaseAuth;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        laetEmail = findViewById(R.id.laetEmail);
        laetPassword = findViewById(R.id.laetPassword);
        laBtnLogin = findViewById(R.id.laBtnLogin);
        laBtnReg = findViewById(R.id.laBtnReg);
        laProgressBar = findViewById(R.id.laProgressBar);
        laForgotPass = findViewById(R.id.laForgotPass);

        firebaseAuth = FirebaseAuth.getInstance();

        Gson gson = new Gson();

        user = new User();

        laBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = laetEmail.getText().toString().trim();
                String password = laetPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    laetEmail.setError(
                            getResources().getString(R.string.emailCap) + " " + getResources().getString(R.string.raRequired)
                    );
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    laetPassword.setError(
                            getResources().getString(R.string.passwordCap) + " " + getResources().getString(R.string.raRequired)
                    );
                    return;
                }

                laProgressBar.setVisibility(View.VISIBLE);

                /*
                Login User in FareBase
                 */
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            user.setId(firebaseAuth.getUid());
                            firebaseFirestore.collection("users")
                                    .whereEqualTo("id",user.getId())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    user = document.toObject(User.class);

                                                    String json = gson.toJson(user);

                                                    try {
                                                        FileOutputStream fOut = openFileOutput ( "userData.json" , MODE_PRIVATE ) ;
                                                        OutputStreamWriter osw = new OutputStreamWriter ( fOut ) ;
                                                        osw.write ( json.toString() ) ;
                                                        osw.flush ( ) ;
                                                        osw.close ( ) ;
                                                        } catch ( Exception e ) {
                                                            e.printStackTrace ( ) ;
                                                        }

/*reade teste
                                                    StringBuffer datax = new StringBuffer("");
                                                    try {
                                                        FileInputStream fIn = openFileInput ( "userData.json" ) ;
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

*/

                                                    Toast.makeText(Login.this,getResources().getString(R.string.loginConfirmation), Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(Login.this,MainActivity.class);
                                                    startActivity(intent);
                                                }
                                            } else {
                                                laProgressBar.setVisibility(View.INVISIBLE);
                                                String taskMessage = task.getException().getLocalizedMessage();
                                                Toast.makeText(Login.this,getResources().getString(R.string.someError) + ": " + taskMessage, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            //Log.d("teste2", user.getClass() + " => " + user.getId() + " - " + user.getName() + " - " + user.getEmail());
                            //Toast.makeText(Login.this,getResources().getString(R.string.loginConfirmation), Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            laProgressBar.setVisibility(View.INVISIBLE);
                            String taskMessage = task.getException().getLocalizedMessage();
                            Toast.makeText(Login.this,getResources().getString(R.string.someError) + ": " + taskMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        laBtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        laForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ResetPass.class));
            }
        });
    }
}