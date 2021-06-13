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
    EditText etRegFullName,etRegEmail,etRegPassword,etRegPasswordConfirm;
    Button btnReg;
    TextView tvAlAuth;
    ProgressBar pbReg;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegFullName = findViewById(R.id.etRegFullName);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        etRegPasswordConfirm = findViewById(R.id.etRegPasswordConfirm);
        btnReg = findViewById(R.id.btnReg);
        tvAlAuth = findViewById(R.id.tvAlAuth);
        pbReg = findViewById(R.id.pbReg);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etRegEmail.getText().toString().trim();
                String password = etRegPassword.getText().toString().trim();
                String confirmPassword = etRegPasswordConfirm.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    etRegEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    etRegPassword.setError("Password is Required.");
                    return;
                }

                if(!password.equals(confirmPassword)){
                    etRegPasswordConfirm.setError("Password and confirmation, mast be the same");
                    return;
                }

                if(password.length() < 4){
                    etRegPassword.setError("Password Must be >= 4 Characteres");
                    return;
                }
                pbReg.setVisibility(View.VISIBLE);

                /*
                Register User in FareBase
                 */

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"User Created sucessfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Register.this,"Same error occurred: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}