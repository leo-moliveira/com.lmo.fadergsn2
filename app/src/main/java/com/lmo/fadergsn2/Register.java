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
    EditText rgaetFullName,rgaetEmail,rgaetPassword,rgaetPasswordConfirm;
    Button rgaBtnRegister;
    TextView rgaBtnLogin;
    ProgressBar rgaProgressBar;

    User user;
    UserFirebase userBase;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rgaetFullName = findViewById(R.id.rgaetFullName);
        rgaetEmail = findViewById(R.id.rgaetEmail);
        rgaetPassword = findViewById(R.id.rgaetPassword);
        rgaetPasswordConfirm = findViewById(R.id.rgaetPasswordConfirm);
        rgaBtnRegister = findViewById(R.id.rgaBtnRegister);
        rgaBtnLogin = findViewById(R.id.rgaBtnLogin);
        rgaProgressBar = findViewById(R.id.rgaProgressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        rgaBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = rgaetEmail.getText().toString().trim();
                String password = rgaetPassword.getText().toString().trim();
                String confirmPassword = rgaetPasswordConfirm.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    rgaetEmail.setError(
                            getResources().getString(R.string.emailCap) + " " + getResources().getString(R.string.raRequired)
                    );
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    rgaetPassword.setError(
                            getResources().getString(R.string.passwordCap) + " " + getResources().getString(R.string.raRequired)
                    );
                    return;
                }

                if(TextUtils.isEmpty(confirmPassword)){
                    rgaetPassword.setError(
                            getResources().getString(R.string.passwordConfirmationCap ) + " " + getResources().getString(R.string.raRequired)
                    );
                    return;
                }

                if(!password.equals(confirmPassword)){
                    rgaetPasswordConfirm.setError(
                            getResources().getString(R.string.raPassConfError)
                    );
                    return;
                }

                if(password.length() < Config.PASSWORD_LENGTH){
                    rgaetPassword.setError(
                            getResources().getString(R.string.raPassValidation) + " " + String.valueOf(Config.PASSWORD_LENGTH ) + " " + getResources().getString(R.string.characters)
                    );
                    return;
                }
                rgaProgressBar.setVisibility(View.VISIBLE);

                /*
                Register User in FareBase
                 */

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,getResources().getString(R.string.registerConfirmation), Toast.LENGTH_SHORT).show();
                            user = new User(firebaseAuth.getUid(),rgaetFullName.getText().toString().trim(), rgaetEmail.getText().toString().trim());
                            userBase = new UserFirebase(user);
                            userBase.save();
                            Instance.getInstance().user = user;
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            rgaProgressBar.setVisibility(View.INVISIBLE);
                            String taskMessage = task.getException().getLocalizedMessage();
                            Toast.makeText(Register.this,getResources().getString(R.string.someError) + ": " + taskMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        rgaBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
    }
}