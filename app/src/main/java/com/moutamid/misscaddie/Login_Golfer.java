package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login_Golfer extends AppCompatActivity {

    TextView signInBtn;
    FirebaseAuth mAuth;
    FirebaseUser currrentUser;
    ProgressDialog dialog;
    private EditText emailTxt,passTxt;
    private DatabaseReference db;
    private String email,password;
    private SharedPreferencesManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_golfer);
        manager = new SharedPreferencesManager(Login_Golfer.this);
        mAuth = FirebaseAuth.getInstance();
        currrentUser = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Golfer");
        signInBtn = findViewById(R.id.signInBtn);
        emailTxt = findViewById(R.id.email);
        passTxt = findViewById(R.id.password);
        email = getIntent().getStringExtra("email");
        emailTxt.setText(email);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validInfo()){
                    dialog = new ProgressDialog(Login_Golfer.this);
                    dialog.setMessage("Login into your account....");
                    dialog.show();
                    loginAccount();
                }
            }
        });
    }

    private void loginAccount() {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            manager.storeString("module","golfer");
                            Intent intent = new Intent(Login_Golfer.this , Dashboard_Golfer.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            Animatoo.animateZoom(Login_Golfer.this);
                            dialog.dismiss();
                        }else {
                            Toast.makeText(Login_Golfer.this,"Wrong Password! Please try again...",Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                });
    }

    public boolean validInfo() {
        email = emailTxt.getText().toString();
        password = passTxt.getText().toString();

        if(email.isEmpty()){
            emailTxt.setText("Input Email");
            emailTxt.requestFocus();
            return false;
        }
        if(password.isEmpty()){
            passTxt.setText("Input Password");
            passTxt.requestFocus();
            return false;
        }
        return true;
    }
}