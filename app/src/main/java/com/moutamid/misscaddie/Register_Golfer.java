package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.databinding.ActivityRegisterGolferBinding;
import com.moutamid.misscaddie.models.Model_Caddie;
import com.moutamid.misscaddie.models.Model_Golfer;

public class Register_Golfer extends AppCompatActivity {

 //   TextView cont_btn;
    private ActivityRegisterGolferBinding b;
    private static final int RC_SIGN_IN = 234;
    GoogleApiClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    ProgressDialog dialog;
    private DatabaseReference db,db1;
    private SharedPreferencesManager manager;
    boolean caddieEmail,golferEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityRegisterGolferBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        mAuth = FirebaseAuth.getInstance();
      //  currrentUser = mAuth.getCurrentUser();
        manager = new SharedPreferencesManager(Register_Golfer.this);
        dialog = new ProgressDialog(Register_Golfer.this);
        db = FirebaseDatabase.getInstance().getReference().child("Golfer");
        db1 = FirebaseDatabase.getInstance().getReference().child("Caddie");
        //cont_btn = findViewById(R.id.cont_btn2);
        b.contBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = b.email.getText().toString();
                dialog.setTitle("Creating your account");
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                if (!TextUtils.isEmpty(email)) {
                    checkingExistance(email);
                }else {
                    b.email.setError("Please Enter your email....");
                    b.email.requestFocus();
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(Register_Golfer.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        b.googleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void checkingExistance(String email) {
        Query query = db.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Intent intent = new Intent(Register_Golfer.this, Login_Golfer.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                    Animatoo.animateZoom(Register_Golfer.this);
                    dialog.dismiss();
                }else {
                    Query query1 = db1.orderByChild("email").equalTo(email);
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Toast.makeText(Register_Golfer.this, "Email already taken!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else {
                                Intent intent = new Intent(Register_Golfer.this, SignUp_Golfer.class);
                                intent.putExtra("email",email);
                                startActivity(intent);
                                Animatoo.animateZoom(Register_Golfer.this);
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(
                mGoogleSignInClient
        );
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialog.setTitle("Logging into your Account");
        dialog.setMessage("Please wait, while logging your Account.....");
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        if (requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            try {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } catch (Exception e) {
                Toast.makeText(Register_Golfer.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    checkUserExists(account.getEmail(),account);
                    // Toast.makeText(Login.this, "User Signed In", Toast.LENGTH_SHORT).show();
                } else {

                    dialog.dismiss();
                    Toast.makeText(Register_Golfer.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    private void checkUserExists(String email, GoogleSignInAccount account) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        Query query = db.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    manager.storeString("module","golfer");
                    Intent intent = new Intent(Register_Golfer.this, Dashboard_Golfer.class);
                    //intent.putExtra("email",email);
                    startActivity(intent);
                    Animatoo.animateZoom(Register_Golfer.this);
                    dialog.dismiss();
                }else {

                    Query query1 = db1.orderByChild("email").equalTo(email);
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Toast.makeText(Register_Golfer.this, "Email already taken!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else {
                                manager.storeString("module","golfer");
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                Model_Golfer model_caddie = new Model_Golfer(firebaseUser.getUid()
                                        ,account.getDisplayName(),account.getEmail(),"",
                                        account.getPhotoUrl().toString());
                                db.child(firebaseUser.getUid()).setValue(model_caddie);
                                sendActivityToSignUp();
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendActivityToSignUp(){
        Intent intent = new Intent(Register_Golfer.this, Dashboard_Golfer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        Animatoo.animateZoom(Register_Golfer.this);
    }

}