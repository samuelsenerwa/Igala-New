package com.example.igalanews.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.igalanews.FacebookAuthActivity;
import com.example.igalanews.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.Arrays;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputLayout mEmail, mPassword;
    Button loginBtn;
    TextView forgot_pass;
    private TextView sign_in_account, sign_up_user;

    ProgressDialog dialog;

    CountryCodePicker countryCodePicker;
    EditText phoneNumber;
    TextInputLayout password;
    ProgressBar progressBar;

    public static  final  String SHARED_PREFS = "sharedPrefs";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = (TextInputLayout) findViewById(R.id.et_Email);
        mPassword = (TextInputLayout) findViewById(R.id.et_Pass);
        loginBtn =  findViewById(R.id.loginBtn);
        forgot_pass = findViewById(R.id.forgotPassword);
        sign_in_account = findViewById(R.id.sign_in_account);
        sign_up_user = findViewById(R.id.sign_up_user);
        phoneNumber = findViewById(R.id.login_phone_number);
        password = (TextInputLayout) findViewById(R.id.et_Pass);

        dialog = new ProgressDialog(this, dialog.THEME_DEVICE_DEFAULT_DARK);
        mAuth = FirebaseAuth.getInstance();

        checkBox();

        //user signup to next activity
        sign_up_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, UserSignUpActivity.class));
                finish();
            }
        });

        //forgot password activity
        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        sign_in_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    finish();
                    startActivity(new Intent(LoginActivity.this, SignUpUserActivity.class));
            }
        });

        //login user with email and password

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    mEmail.setError("Email is Required");
                    mEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(LoginActivity.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    mEmail.setError("Enter valid email");
                    mEmail.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    mPassword.setError("Email is Required");
                    mPassword.requestFocus();
                } else {
                    loginUser(email, password);
                }
            }
        });
//
    }

    private void checkBox() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String check = sharedPreferences.getString("name", "");
        if (check.equals("true")){
            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //Login user using phone number and password
    public  void letUserLogin(View view){
        if (!validateFields()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        //retrieve data
        String _phoneNumber = phoneNumber.getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        if (_phoneNumber.charAt(0)=='0'){
            _phoneNumber = _phoneNumber.substring(1);
        }

//        String completePhoneNumber = "+"+countryCodePicker.getFullNumber()+_phoneNumber;
        String completePhoneNumber = "+254" + _phoneNumber;

        //query database
        Query checkUser = FirebaseDatabase.getInstance().getReference("users").orderByChild("phoneNo").equalTo(_phoneNumber);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    phoneNumber.setError(null);

                    String _fullname = snapshot.child(completePhoneNumber).child("fullName").getValue(String.class);
                    String _email = snapshot.child(completePhoneNumber).child("email").getValue(String.class);
                    String _phoneNo = snapshot.child(completePhoneNumber).child("phoneNo").getValue(String.class);

                    Toast.makeText(LoginActivity.this, _fullname+"\n"+_email+"\n"+_phoneNo, Toast.LENGTH_LONG).show();

                    String systemPassword = snapshot.child(completePhoneNumber).child("password").getValue(String.class);
                    if(systemPassword.equals(_password)){
                        password.setError(null);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean validateFields() {

        String _phoneNumber = phoneNumber.getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        if (_phoneNumber.isEmpty()){
            phoneNumber.setError("Phone number cannot be empty");
            phoneNumber.requestFocus();
            return false;
        }
        else  if (_password.isEmpty()){
            password.setError("Password can not be empty");
            password.requestFocus();
            return false;
        }
        else {
            phoneNumber.setError(null);
            return  true;
        }

    }
    private void loginUser(String email, String password) {
        dialog.setTitle("Please Wait...");
        dialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //keep the user logged in
                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("name", "true");
                            editor.apply();

                            Toast.makeText(LoginActivity.this, "You're now logged in", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));

                        } else {
                            Toast.makeText(LoginActivity.this, "Something Went wrong!!", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();

                    }
                });
    }
}