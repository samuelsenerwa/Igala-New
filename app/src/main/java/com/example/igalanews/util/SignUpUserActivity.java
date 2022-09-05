package com.example.igalanews.util;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.igalanews.FacebookAuthActivity;
import com.example.igalanews.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class SignUpUserActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mEmail, mPassword;
    Button sign_up_btn;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
   // ImageView googleBtn, facebookBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_user);

//        googleBtn = findViewById(R.id.googleBtn);
        mEmail = findViewById(R.id.et_Email);
        mPassword = findViewById(R.id.et_Pass);
//        sign_up_btn = findViewById(R.id.sign_up_btn);
//        facebookBtn = findViewById(R.id.facebookBtn);
        mAuth = FirebaseAuth.getInstance();


        //signUp use with email and password

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignUpUserActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    mEmail.setError("Email is Required");
                    mEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(SignUpUserActivity.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    mEmail.setError("Enter valid email");
                    mEmail.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUpUserActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    mPassword.setError("Email is Required");
                    mPassword.requestFocus();
                } else {
                    createUser(email, password);
                }
            }

        });

        //Hide password

        ImageView imageView_pass = findViewById(R.id.hide_password);
        imageView_pass.setImageResource(R.drawable.ic_view_pass);
        imageView_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageView_pass.setImageResource(R.drawable.ic_hide_pass);
                } else {
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageView_pass.setImageResource(R.drawable.ic_view_pass);
                }
            }
        });

//        //facebook login
//        facebookBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SignUpUserActivity.this, FacebookAuthActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent);
//
//                 //LoginManager.getInstance().logInWithReadPermissions(SignUpUserActivity.this, Arrays.asList("public_profile"));
//            }
//        });

        //signup the user google Account


        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            //  navigateToSecondActivity();
        }


//        googleBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//            }
//        });
//    }
        //method to sign-up user

//
//    private void signIn() {
//        Intent signInIntent = googleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, 1000);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1000){
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//
//            try {
//                task.getResult(ApiException.class);
//                navigateToSecondActivity();
//
//            } catch (ApiException e) {
//                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }
//    private void navigateToSecondActivity(){
//        finish();
//        Intent intent = new Intent(SignUpUserActivity.this, MainActivity.class);
//        startActivity(intent);
//    }
}

    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignUpUserActivity.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpUserActivity.this, LoginActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpUserActivity.this, "Registration Failed!! Try using google or Facebook", Toast.LENGTH_LONG).show();
            }
        });
    }
}