package com.example.igalanews.util;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class SignUpUserActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button next, login;
    TextView create_title;
    ImageView title_drawable, back_btn;
    TextInputLayout fullName, userName, password, email, location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_user);

        next = findViewById(R.id.button_sign_in_next);
        login = findViewById(R.id.signup_login);
        back_btn = findViewById(R.id.back_button);
        create_title = findViewById(R.id.create_text_title);
        title_drawable = findViewById(R.id.title_drawable);
        location = (TextInputLayout) findViewById(R.id.spinner);
        fullName = (TextInputLayout) findViewById(R.id.full_name);
        userName = (TextInputLayout) findViewById(R.id.user_name);
        email = (TextInputLayout) findViewById(R.id.et_Email);
        password = (TextInputLayout) findViewById(R.id.et_Pass);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpUserActivity.this, LoginActivity.class));
                finish();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpUserActivity.this, LoginActivity.class));
                finish();
            }
        });


        String[] local_government = new String[] {
                "Ankpa",
                "Ibaji",
                "Idah",
                "Ofu",
                "Omala",
                "Olamaboro",
                "Dekina",
                "Igalamela",
                "Ajaokuta"
        };

        ArrayAdapter <String> stringArrayAdapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_item,
                        local_government
                );

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.filled_exposed);
        autoCompleteTextView.setAdapter(stringArrayAdapter);

      autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              Toast.makeText(SignUpUserActivity.this, autoCompleteTextView.getText().toString(), Toast.LENGTH_SHORT).show();
          }
      });

    }

    private boolean validateFullName(){
        String val = fullName.getEditText().getText().toString().trim();

        if (val.isEmpty()){
            fullName.setError("Filed cannot be empty");
            return false;
        }
        else
        {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName(){
        String val = userName.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{4,20}\\z";
        if (val.isEmpty()){
            fullName.setError("Filed cannot be empty");
            return false;
        } else if (val.length() > 20){
            userName.setError("Username too long");
            return  false;
        }

        else if (!val.matches(checkspaces)){
            userName.setError("No white spaces allowed");
            return  false;
        }

        else
        {
            userName.setError(null);
            userName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateSpinner(){
        String val = location.getEditText().getText().toString().trim();

        if (val.isEmpty()){
            location.setError("Filed cannot be empty");
            return false;
        }
        else
        {
            location.setError(null);
            location.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail(){
        String val = email.getEditText().getText().toString().trim();
        String checkemail = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()){
            email.setError("Filed cannot be empty");
            return false;
        }

        else if (!val.matches(checkemail)){
            email.setError("Invalid email");
            return  false;
        }

        else
        {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword(){
        String val = password.getEditText().getText().toString().trim();
        String checkPassword =
                "(?=.*[a-zA-Z])" + //any letter
                "(?=.*[@#$%^&+=])" + //no whitespaces
                "(?=\\S+$)" +
                ".{4,}" +
                        "$"
                ;
        if (val.isEmpty()){
            password.setError("Filed cannot be empty");
            return false;
        }

        else if (!val.matches(checkPassword)){
            password.setError("Password should contain atleast 4 characters");
            return  false;
        }

        else
        {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }



    public void callNextSignupScreen(View view){

        if (!validateFullName() | !validateEmail() | !validateUserName() | !validatePassword() | !validateSpinner()) {
                    return;
                }

        String full_name = fullName.getEditText().getText().toString();
        String user_name = userName.getEditText().getText().toString();
        String _location = location.getEditText().getText().toString();
        String pass_word = password.getEditText().getText().toString();
        String userEmail = email.getEditText().getText().toString();


        Intent intent = new Intent(getApplicationContext(), SignUp3rdUserActivity.class);
        intent.putExtra("fullName", full_name);
        intent.putExtra("userName", user_name);
        intent.putExtra("Spinner", _location);
        intent.putExtra("Email", userEmail);
        intent.putExtra("Password", pass_word);
        startActivity(intent);
    }

}