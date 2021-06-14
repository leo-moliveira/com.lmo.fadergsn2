package com.lmo.fadergsn2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPass extends AppCompatActivity {
    EditText rpaetEmail;
    Button rpaBtnReset;
    TextView rpaBtnLogin;
    ProgressBar rpaProgressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        rpaBtnLogin = findViewById(R.id.rpaBtnLogin);
        rpaetEmail = findViewById(R.id.rpaetEmail);
        rpaBtnReset = findViewById(R.id.rpaBtnReset);
        rpaProgressBar = findViewById(R.id.rpaProgressBar);
        firebaseAuth = FirebaseAuth.getInstance();

        rpaBtnReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String mail = rpaetEmail.getText().toString().trim();

                if(TextUtils.isEmpty(mail)){
                    rpaetEmail.setError(
                            getResources().getString(R.string.emailCap) + " " + getResources().getString(R.string.raRequired)
                    );
                    return;
                }

                AlertDialog.Builder confirmation = new AlertDialog.Builder(view.getContext());
                confirmation.setTitle(getResources().getString(R.string.rpatvSubTitle));
                confirmation.setMessage(getResources().getString(R.string.ResetConfirmation));

                confirmation.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ResetPass.this,getResources().getString(R.string.passwordResetSucessSent),Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),Login.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ResetPass.this,getResources().getString(R.string.someError) + getResources().getString(R.string.notsent) + ":" + e.getMessage(), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),Login.class));
                                finish();
                            }
                        });

                    }
                });

                confirmation.setNeutralButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(),Login.class));
                        finish();
                    }
                });
                confirmation.show();
            }
        });

        rpaBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
    }
}