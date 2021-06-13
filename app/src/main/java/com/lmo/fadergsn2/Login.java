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

public class Login extends AppCompatActivity {
    EditText laetPassword, laetEmail;
    TextView laBtnReg,laForgotPass;
    Button laBtnLogin;
    ProgressBar laProgressBar;

    FirebaseAuth firebaseAuth;

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
                            Toast.makeText(Login.this,getResources().getString(R.string.loginConfirmation), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
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