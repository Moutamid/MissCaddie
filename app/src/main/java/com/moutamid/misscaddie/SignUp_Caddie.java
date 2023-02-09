package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
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
import com.moutamid.misscaddie.models.Model_Caddie;
import com.moutamid.misscaddie.models.Model_Golfer;

public class SignUp_Caddie extends AppCompatActivity {

    TextView signUpBtn, privacybtn, termsbtn;

    FirebaseAuth mAuth;
    FirebaseUser currrentUser;
    ProgressDialog dialog;
    private DatabaseReference db,db1;
    private String fname,lname,email,password,cpassword;
    boolean caddieEmail,golferEmail;
    private EditText emailTxt,fnameTxt,lnameTxt,passTxt,cpassTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_caddie);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarYellow));
        }
        emailTxt = findViewById(R.id.email);
        fnameTxt = findViewById(R.id.Fname);
        lnameTxt = findViewById(R.id.Lname);
        passTxt = findViewById(R.id.password);
        cpassTxt = findViewById(R.id.cnfrm_password);
        signUpBtn = findViewById(R.id.signUpBtn2);
        privacybtn = findViewById(R.id.privacyBtn);
        termsbtn = findViewById(R.id.termsbtn);

        mAuth = FirebaseAuth.getInstance();
   //     currrentUser = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        db1 = FirebaseDatabase.getInstance().getReference().child("Golfer");
        email = getIntent().getStringExtra("email");
        emailTxt.setText(email);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validInfo()){
                    dialog = new ProgressDialog(SignUp_Caddie.this);
                    dialog.setMessage("Creating yout account....");
                    dialog.show();
                    createAccount();
                }
            }
        });
        changeStatusBarColor(this,R.color.yellow);
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
                            fname + " " + lname,email,password,"","","","",0,0,"","");
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

    public void changeStatusBarColor(Activity activity, int id) {

        // Changing the color of status bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activity.getResources().getColor(id));
        }

        // CHANGE STATUS BAR TO TRANSPARENT
        //window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }


    public boolean validInfo() {
        fname = fnameTxt.getText().toString();
        lname = lnameTxt.getText().toString();
        email = emailTxt.getText().toString();
        password = passTxt.getText().toString();
        cpassword = cpassTxt.getText().toString();

        if(fname.isEmpty()){
            fnameTxt.setText("Input First Name");
            fnameTxt.requestFocus();
            return false;
        }

        if(lname.isEmpty()){
            lnameTxt.setText("Input Last Name");
            lnameTxt.requestFocus();
            return false;
        }
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
        if(cpassword.isEmpty()){
            cpassTxt.setText("Input Confirm Password");
            cpassTxt.requestFocus();
            return false;
        }

        if (!password.equals(cpassword)){
            Toast.makeText(SignUp_Caddie.this,"Password are not matched",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}