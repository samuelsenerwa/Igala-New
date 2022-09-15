package com.example.igalanews.util;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.igalanews.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserSignUpActivity extends AppCompatActivity {

    ImageView back_button;
    Button  SignUp_User;
    TextInputLayout mEmail, mPassword;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        back_button = findViewById(R.id.back_button);
        SignUp_User = findViewById(R.id.user_sign_up_btn);
        mEmail = (TextInputLayout) findViewById(R.id.et_Email);
        mPassword = (TextInputLayout) findViewById(R.id.et_Pass);

        mAuth = FirebaseAuth.getInstance();


        // create user to signUp
        SignUp_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        //return to previous activity
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserSignUpActivity.this, LoginActivity.class));
            }
        });

    }

    private void createUser() {
        String email = mEmail.getEditText().getText().toString().trim();
        String password = mPassword.getEditText().getText().toString().trim();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            if (!password.isEmpty()){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(UserSignUpActivity.this, "Registered Successfully!!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(UserSignUpActivity.this, SignUpUserActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserSignUpActivity.this, "Registration Failed! Create Account", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        //validate password
        validatePassword();

        //validate email
        validateEmail();


    }

    private boolean validateEmail(){
        String val = mEmail.getEditText().getText().toString().trim();
        String checkemail = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()){
            mEmail.setError("Filed cannot be empty");
            return false;
        }

        else if (!val.matches(checkemail)){
            mEmail.setError("Invalid email");
            return  false;
        }

        else
        {
            mEmail.setError(null);
            mEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword(){
        String val = mPassword.getEditText().getText().toString().trim();
        String checkPassword =
                "(?=.*[a-zA-Z])" + //any letter
                        "(?=.*[@#$%^&+=])" + //no whitespaces
                        "(?=\\S+$)" +
                        ".{4,}" +
                        "$"
                ;
        if (val.isEmpty()){
           mPassword.setError("Filed cannot be empty");
            return false;
        }

        else if (!val.matches(checkPassword)){
            mPassword.setError("Password should contain atleast 4 characters");
            return  false;
        }

        else
        {
            mPassword.setError(null);
            mPassword.setErrorEnabled(false);
            return true;
        }
    }
}