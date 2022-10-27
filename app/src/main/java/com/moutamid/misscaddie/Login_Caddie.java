package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.databinding.ActivityLoginCaddieBinding;
import com.moutamid.misscaddie.databinding.ActivityLoginGolferBinding;
import com.moutamid.misscaddie.models.Model_Caddie;

public class Login_Caddie extends AppCompatActivity {
    TextView signInBtn;
    private ActivityLoginCaddieBinding b;
    FirebaseAuth mAuth;
    FirebaseUser currrentUser;
    ProgressDialog dialog;
    private DatabaseReference db;
    private String email,password;
    private SharedPreferencesManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityLoginCaddieBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        manager = new SharedPreferencesManager(Login_Caddie.this);
        mAuth = FirebaseAuth.getInstance();
        currrentUser = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        signInBtn = findViewById(R.id.signInBtn2);
        email = getIntent().getStringExtra("email");
        b.email.setText(email);
        changeStatusBarColor(this,R.color.yellow);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validInfo()){
                    dialog = new ProgressDialog(Login_Caddie.this);
                    dialog.setMessage("Login into your account....");
                    dialog.show();
                    loginAccount();
                }
            }
        });
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
    private void loginAccount() {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            checkInfoExists();
                        }else {
                            Toast.makeText(Login_Caddie.this,"Wrong Password! Please try again...",Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                });
    }

    private void checkInfoExists() {
        db.child(mAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Model_Caddie caddie = snapshot.getValue(Model_Caddie.class);
                            if (!caddie.getState().equals("") && !caddie.getCatagory().equals("") &&
                                    !caddie.getStatus().equals("") && !caddie.getPlace().equals("")){
                                manager.storeString("module","caddie");
                                Intent intent = new Intent(Login_Caddie.this , CaddieDashboardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                                Animatoo.animateZoom(Login_Caddie.this);
                                dialog.dismiss();
                            }else {
                                manager.storeString("module","caddie");
                                Intent intent = new Intent(Login_Caddie.this , CaddieDeatilsActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                                Animatoo.animateZoom(Login_Caddie.this);
                                dialog.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public boolean validInfo() {
        email = b.email.getText().toString();
        password = b.password.getText().toString();

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
        return true;
    }
}