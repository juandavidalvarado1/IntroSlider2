package com.shubham.introslider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.shubham.introslider.databinding.ActivityMainBinding;

import java.util.concurrent.Executor;


public class ThirdFragment extends Fragment  {


    private static final int SIGN_IN_CODE = 777;
  //  private GoogleApiClient googleApiClient;
private FirebaseAuth firebaseAuth;
private FirebaseAuth.AuthStateListener firebaseAuthListener;
private GoogleSignInClient googleSignInClient;

    public Button btnLogin;
    public TextView txt1;

    private ActivityMainBinding binding;
    private SignInButton googleSignInBtn;
    // GoogleSignInOptions mGoogleApiClient;
   // private com.google.android.gms.auth.api.signin.GoogleSignInClient GoogleSignInClient;
;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_third, container, false);
        btnLogin = root.findViewById(R.id.btnlog1);
        txt1 = root.findViewById(R.id.textView42);
        googleSignInBtn = (SignInButton) root.findViewById(R.id.googleSignInBtn);

        GoogleSignInOptions gso=new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken("351740939245-uemsnlsr23d9igi3cdolpukmhsipg4it.apps.googleusercontent.com")
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //googleSignInClient = GoogleSignIn.getClient(getContext(), gso);



        Context context=getContext();


       //  googleApiClient = new GoogleApiClient.Builder(getActivity())
        //        .enableAutoManage(getActivity() /* FragmentActivity */,
        //                new GoogleApiClient.OnConnectionFailedListener() {
        //                    @Override
        //                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //                        Toast.makeText(context,"algo anda mal",Toast.LENGTH_SHORT).show();
        //                    }
        //                } /* OnConnectionFailedListener */)
        //        .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
        //        .build();






        googleSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG","comienza login");
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
               // Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                Intent intent= mGoogleSignInClient.getSignInIntent();
                //Intent intent = Auth.GoogleSignInApi.getSignInIntent();
                startActivityForResult(intent,SIGN_IN_CODE);
               // startActivity(intent,SIGN_IN_CODE);

            }
        });

        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new ThirdFragmentLo();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, myFragment).commit();

               //Intent intentlog3 = new Intent(root.getContext(), ThirdFragmentLo.class);
               // startActivity(intentlog3);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLog = new Intent(root.getContext(), RegisterActivity.class);
                startActivity(intentLog);
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();



        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user != null){
                    goMainScreen();
                }
            }
        };



       /* ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                           // doSomeOperations();
                            GoogleSignInResult result2=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                            handleSignInResult(result2);
                        }
                    }
                });*/
        return root;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
        if(requestCode == SIGN_IN_CODE){
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                try {
                    GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
            handleSignInResult(result);

        }
    }



    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            firebaseAuthWithGoogle(result.getSignInAccount());

        }else{
            Toast.makeText(getActivity(),"no se puede iniciar sesión",Toast.LENGTH_SHORT).show();
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount){
        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d("TAG","Logged in");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","Logged failure");
            }
        });
       // firebaseAuth.signInWithCredential(credential).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
        /*    @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getActivity().getApplicationContext(), R.string.not_firebase_auth, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity().getBaseContext(),"no credencial",Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }


    private void goMainScreen(){
       // Intent intent = new Intent(getActivity(),this.getClass());
        Intent intent = new Intent(this.getContext(), GoogleActivity.class);
        //intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
       // Intent intentLog2 = new Intent(this.getContext(), GoogleActivity.class);
       // startActivity(intentLog2);
    }
    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(firebaseAuthListener != null){
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }



}