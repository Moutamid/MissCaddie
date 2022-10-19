package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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
import com.moutamid.misscaddie.databinding.ActivitySignUpCaddieBinding;
import com.moutamid.misscaddie.databinding.ActivitySignUpGolferBinding;
import com.moutamid.misscaddie.models.Model_Caddie;
import com.moutamid.misscaddie.models.Model_Golfer;

public class SignUp_Caddie extends AppCompatActivity {

    TextView signUpBtn, privacybtn, termsbtn;

    private ActivitySignUpCaddieBinding b;
    FirebaseAuth mAuth;
    FirebaseUser currrentUser;
    ProgressDialog dialog;
    private DatabaseReference db;
    private String fname,lname,email,password,cpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivitySignUpCaddieBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarYellow));
        }

        signUpBtn = findViewById(R.id.signUpBtn2);
        privacybtn = findViewById(R.id.privacyBtn);
        termsbtn = findViewById(R.id.termsbtn);

        mAuth = FirebaseAuth.getInstance();
   //     currrentUser = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        email = getIntent().getStringExtra("email");
        b.email.setText(email);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(SignUp_Caddie.this , Login_Caddie.class);
                startActivity(intent);
                Animatoo.animateZoom(SignUp_Caddie.this);*/
                if (validInfo()){
                    dialog = new ProgressDialog(SignUp_Caddie.this);
                    dialog.setMessage("Creating yout account....");
                    dialog.show();
                    createAccount();
                }
            }
        });

        termsbtn.setOnClickListener(v -> {
            Uri webpage = Uri.parse("https://www.google.com");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            try {
                startActivity(webIntent);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        privacybtn.setOnClickListener(v -> {
            Uri webpage = Uri.parse("https://www.google.com");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            try {
                startActivity(webIntent);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

    }


    private void createAccount() {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    currrentUser = mAuth.getCurrentUser();
                    Model_Caddie model_caddie = new Model_Caddie(currrentUser.getUid(),
                            fname + " " + lname,email,password,"","","","","","");
                    db.child(currrentUser.getUid()).setValue(model_caddie);
                    sendActivityToLogin();
                    dialog.dismiss();
                }
            }
        });
    }

    private void sendActivityToLogin() {

        Intent intent = new Intent(SignUp_Caddie.this, Login_Caddie.class);
        intent.putExtra("email",email);
       // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        Animatoo.animateZoom(SignUp_Caddie.this);
    }

    public boolean validInfo() {
        fname = b.Fname.getText().toString();
        lname = b.Lname.getText().toString();
        email = b.email.getText().toString();
        password = b.password.getText().toString();
        cpassword = b.cnfrmPassword.getText().toString();

        if(fname.isEmpty()){
            b.Fname.setText("Input First Name");
            b.Fname.requestFocus();
            return false;
        }

        if(lname.isEmpty()){
            b.Lname.setText("Input Last Name");
            b.Lname.requestFocus();
            return false;
        }
        if(email.isEmpty()){
            b.email.setText("Input Email");
            b.email.requestFocus();
            return false;
        }
        if(password.isEmpty()){
            b.password.setText("Input Password");
            b.password.requestFocus();
            return false;
        }
        if(cpassword.isEmpty()){
            b.cnfrmPassword.setText("Input Confirm Password");
            b.cnfrmPassword.requestFocus();
            return false;
        }

        if (!password.equals(cpassword)){
            Toast.makeText(SignUp_Caddie.this,"Password are not matched",Toast.LENGTH_LONG).show();
        }
        return true;
    }

}