package com.shubham.introslider;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.IOException;

public class GoogleActivity extends AppCompatActivity {


   // private SmsRetriever GoogleSignIn;
    private ImageView imageView;
    private Button button2;
    private Button button3;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;

    //private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Build a GoogleSignInClient with the options specified by gso.

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);

        imageView = findViewById(R.id.imageView6);
        textView1 = findViewById(R.id.textView23);
        textView2 = findViewById(R.id.textView38);
        textView3 = findViewById(R.id.textView39);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);


       GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
       // Object mGoogleSignInClient = GoogleSignIn.getClient(this);




        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    /*    googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();*/



       // GoogleSignInClient mGoogleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(this, gso);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    setUserData(user);

                }else{

                    goLogInScreen();
                }
            }
        };

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Auth.GoogleSignInApi.signOut(mGoogleSignInClient.asGoogleApiClient());
                firebaseAuth.signOut();
                //FirebaseAuth.getInstance().signOut();
                goLogInScreen();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ActivityHome.class);
                startActivity(intent);
            }
        });

    }

    private void setUserData(FirebaseUser user){
        textView1.setText(user.getDisplayName());
        textView2.setText(user.getEmail());
        textView3.setText(user.getUid());
        Glide.with(this).load(user.getPhotoUrl()).into(imageView);

    }



    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

  /*  private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            textView1.setText(account.getDisplayName());
            textView2.setText(account.getEmail());
            textView3.setText(account.getId());


            Glide.with(this).load(account.getPhotoUrl()).into(imageView);
        }else{
            goLogInScreen();
        }
    }*/

    private void goLogInScreen(){

      /*  AppCompatActivity activity = (AppCompatActivity) this.getBaseContext();
        Fragment myFragment = new ThirdFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, myFragment).commit();*/

       Intent intent = new Intent(this, ActivityHome.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

 /*   public void logOut(View view){
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback((ResultCallback) (status) -> {
            if(status.isSuccess()){
                goLogInScreen();
            }
            else{
                Toast.makeText(getApplicationContext(),"no se pudo cerrar cesion",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void revoke(View view){
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback((ResultCallback) (status) -> {
            if(status.isSuccess()){
                goLogInScreen();
            }
            else{
                Toast.makeText(getApplicationContext(),"no se pudo revocar el acceso",Toast.LENGTH_SHORT).show();
            }
        });
        }


    }*/

    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseAuthListener != null){
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }
}