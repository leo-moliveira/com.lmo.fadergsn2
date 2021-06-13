package com.lmo.fadergsn2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class Register extends AppCompatActivity {
    EditText raetFullName,raetEmail,raetPassword,raetPasswordConfirm;
    Button raetBtn;
    TextView ratvAuth;
    ProgressBar raProgressBar;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        raetFullName = findViewById(R.id.raetFullName);
        raetEmail = findViewById(R.id.raetEmail);
        raetPassword = findViewById(R.id.raetPassword);
        raetPasswordConfirm = findViewById(R.id.raetPasswordConfirm);
        raetBtn = findViewById(R.id.raetBtn);
        ratvAuth = findViewById(R.id.ratvAuth);
        raProgressBar = findViewById(R.id.raProgressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        raetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = raetEmail.getText().toString().trim();
                String password = raetPassword.getText().toString().trim();
                String confirmPassword = raetPasswordConfirm.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    raetEmail.setError(
                            getResources().getString(R.string.emailCap) + " " + getResources().getString(R.string.raRequired)
                    );
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    raetPassword.setError(
                            getResources().getString(R.string.passwordCap) + " " + getResources().getString(R.string.raRequired)
                    );
                    return;
                }

                if(TextUtils.isEmpty(confirmPassword)){
                    raetPassword.setError(
                            getResources().getString(R.string.passwordConfirmationCap ) + " " + getResources().getString(R.string.raRequired)
                    );
                    return;
                }

                if(!password.equals(confirmPassword)){
                    raetPasswordConfirm.setError(
                            getResources().getString(R.string.raPassConfError)
                    );
                    return;
                }

                if(password.length() < Config.PASSWORD_LENGTH){
                    raetPassword.setError(
                            getResources().getString(R.string.raPassValidation) + String.valueOf(Config.PASSWORD_LENGTH ) + getResources().getString(R.string.characters)
                    );
                    return;
                }
                raProgressBar.setVisibility(View.VISIBLE);

                /*
                Register User in FareBase
                 */

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,getResources().getString(R.string.loginConfirmation), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            raProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Register.this,getResources().getString(R.string.someError) + ": " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}