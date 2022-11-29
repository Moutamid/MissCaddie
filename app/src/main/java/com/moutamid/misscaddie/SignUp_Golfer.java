package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.databinding.ActivitySignUpGolferBinding;
import com.moutamid.misscaddie.models.Model_Caddie;
import com.moutamid.misscaddie.models.Model_Golfer;

public class SignUp_Golfer extends AppCompatActivity {

    TextView signUpBtn, privacybtn, termsbtn;
    private ActivitySignUpGolferBinding b;
    FirebaseAuth mAuth;
    FirebaseUser currrentUser;
    ProgressDialog dialog;
    private DatabaseReference db,db1;
    private String fname,lname,email,password,cpassword;
    boolean caddieEmail,golferEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivitySignUpGolferBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        mAuth = FirebaseAuth.getInstance();
      //  currrentUser = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Golfer");
        db1 = FirebaseDatabase.getInstance().getReference().child("Caddie");
        email = getIntent().getStringExtra("email");
        b.email.setText(email);
        signUpBtn = findViewById(R.id.signUpBtn);
        privacybtn = findViewById(R.id.privacyBtn);
        termsbtn = findViewById(R.id.termsbtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validInfo()){
                    dialog = new ProgressDialog(SignUp_Golfer.this);
                    dialog.setMessage("Creating yout account....");
                    dialog.show();
                    createAccount();
                }
              /*  Intent intent = new Intent(SignUp_Golfer.this , Login_Golfer.class);
                startActivity(intent);
                Animatoo.animateZoom(SignUp_Golfer.this);*/
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
                    Model_Golfer model_caddie = new Model_Golfer(currrentUser.getUid(),
                            fname + " " + lname,email,password,"");
                    db.child(currrentUser.getUid()).setValue(model_caddie);
                    sendActivityToLogin();
                    dialog.dismiss();
                }
            }
        });
    }

    private void sendActivityToLogin() {

        Intent intent = new Intent(SignUp_Golfer.this, Login_Golfer.class);
        intent.putExtra("email",email);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        Animatoo.animateZoom(SignUp_Golfer.this);
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
            Toast.makeText(SignUp_Golfer.this,"Password are not matched",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}