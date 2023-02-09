package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
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
import com.moutamid.misscaddie.models.Model_Caddie;

public class Register_Caddie extends AppCompatActivity {

    TextView cont_btn;
    private static final int RC_SIGN_IN = 234;
    GoogleApiClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    FirebaseUser currrentUser;
    ProgressDialog dialog;
    private SharedPreferencesManager manager;
    private DatabaseReference db,db1;
    boolean caddieEmail,golferEmail;
    private EditText emailTxt;
    private Button googleSignUp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_caddie);

        mAuth = FirebaseAuth.getInstance();
        currrentUser = mAuth.getCurrentUser();
        manager = new SharedPreferencesManager(Register_Caddie.this);
        db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        db1 = FirebaseDatabase.getInstance().getReference().child("Golfer");
        dialog = new ProgressDialog(Register_Caddie.this);
        emailTxt = findViewById(R.id.email);
        cont_btn = findViewById(R.id.cont_btn2);
        googleSignUp = findViewById(R.id.googleSignUp);
        cont_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTxt.getText().toString();
                dialog.setTitle("Creating your account");
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                if (!TextUtils.isEmpty(email)) {
                    checkingExistance(email);
                }else {
                    emailTxt.setError("Please Enter your email....");
                    emailTxt.requestFocus();
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
                        Toast.makeText(Register_Caddie.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        googleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        changeStatusBarColor(this,R.color.yellow);
    }

    private void checkingExistance(String email) {
        Query query = db.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Intent intent = new Intent(Register_Caddie.this, Login_Caddie.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                    Animatoo.animateZoom(Register_Caddie.this);
                    dialog.dismiss();
                }else {
                    Query query1 = db1.orderByChild("email").equalTo(email);
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Toast.makeText(Register_Caddie.this, "Email already taken!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else {
                                Intent intent = new Intent(Register_Caddie.this, SignUp_Caddie.class);
                                intent.putExtra("email",email);
                                startActivity(intent);
                                Animatoo.animateZoom(Register_Caddie.this);
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
                Toast.makeText(Register_Caddie.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Register_Caddie.this, "Authentication failed.",
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
                    manager.storeString("module","caddie");
                    Intent intent = new Intent(Register_Caddie.this, CaddieDashboardActivity.class);
                    //intent.putExtra("email",email);
                    startActivity(intent);
                    Animatoo.animateZoom(Register_Caddie.this);
                    dialog.dismiss();
                }else {

                    Query query1 = db1.orderByChild("email").equalTo(email);
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Toast.makeText(Register_Caddie.this, "Email already taken!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else {
                                manager.storeString("module","caddie");
                                Model_Caddie model_caddie = new Model_Caddie(firebaseUser.getUid(),
                                        account.getDisplayName(),account.getEmail(),"",
                                        account.getPhotoUrl().toString(),"","","",0,0,"","");
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
    private void sendActivityToSignUp(){
        Intent intent = new Intent(Register_Caddie.this, CaddieDeatilsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        Animatoo.animateZoom(Register_Caddie.this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mGoogleSignInClient.isConnected()) {
            mGoogleSignInClient.connect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleSignInClient != null && mGoogleSignInClient.isConnected()) {
            mGoogleSignInClient.stopAutoManage(this);
            mGoogleSignInClient.disconnect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleSignInClient != null && mGoogleSignInClient.isConnected()) {
            mGoogleSignInClient.stopAutoManage(this);
            mGoogleSignInClient.disconnect();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGoogleSignInClient != null && mGoogleSignInClient.isConnected()) {
            mGoogleSignInClient.stopAutoManage(this);
            mGoogleSignInClient.disconnect();
        }
    }

}