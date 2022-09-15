package com.example.igalanews.util;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.igalanews.R;
import com.hbb20.CountryCodePicker;

public class SignUp3rdUserActivity extends AppCompatActivity {
    ImageView back_btn;
   // TextInputLayout phoneNumber;
    EditText phoneNumber;
    CountryCodePicker countryCodePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up3rd_user);

        back_btn = findViewById(R.id.back_button);
        phoneNumber = findViewById(R.id.login_phone_number);
        countryCodePicker =findViewById(R.id.login_country_code_picker);

        countryCodePicker.registerCarrierNumberEditText(phoneNumber);

                back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp3rdUserActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    public void callVerifyOTPScreen(View view){
            if (!validatePhoneNumber()){
                return;
            }

        String full_name = getIntent().getStringExtra("fullName");
        String user_name = getIntent().getStringExtra("userName");
        String location = getIntent().getStringExtra("Spinner");
        String  userEmail = getIntent().getStringExtra("Email");
        String pass_word = getIntent().getStringExtra("Password");

                  String _getUserEnteredPhoneNumber = phoneNumber.getText().toString().trim();
                  String phoneNo = "+254" + _getUserEnteredPhoneNumber;
//                  String phoneNo = "+"+countryCodePicker.getFullNumber()+_getUserEnteredPhoneNumber;
//
//                    String code = countryCodePicker.getSelectedCountryCodeWithPlus();
//                    phoneNumber.setText("" + code);
//                    String _getUserEnteredPhoneNumber = phoneNumber.getText().toString().trim();
//
//

                Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

               intent.putExtra("fullName", full_name);

                intent.putExtra("userName", user_name);

                intent.putExtra("Spinner", location);

                intent.putExtra("Email", userEmail);

                intent.putExtra("Password", pass_word);

                intent.putExtra("phoneNumber", phoneNo);

                startActivity(intent);
    }

    private boolean validatePhoneNumber(){
//        String val = phoneNumber.getEditText().getText().toString().trim();
        String _getUserEnteredPhoneNumber = phoneNumber.getText().toString().trim();

        if (_getUserEnteredPhoneNumber.isEmpty()){
            phoneNumber.setError("Missing PhoneNumber");
            return  false;
        }
        else
        {
            phoneNumber.setError(null);
            return true;

        }
    }

}