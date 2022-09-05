package com.example.igalanews.util;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.igalanews.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText forgot_pass_text;
    Button send_pass_btn;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgot_pass_text = findViewById(R.id.forgot_pass_email);
        send_pass_btn = findViewById(R.id.send_pass_btn);

            mAuth = FirebaseAuth.getInstance();
            send_pass_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAuth.sendPasswordResetEmail(forgot_pass_text.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()){
                                       Toast.makeText(ForgotPasswordActivity.this, "Password Sent to your Email", Toast.LENGTH_SHORT).show();
                                   } else {
                                       Toast.makeText(ForgotPasswordActivity.this,
                                               task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                   }
                                }
                            });
                }
            });
    }
}