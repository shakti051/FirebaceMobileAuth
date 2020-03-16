package com.gov.saglik.checkotp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    EditText etMobileNo;
    Button btnVerify;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks  verificationStateChangedCallback;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etMobileNo = findViewById(R.id.etMobileNo);
        btnVerify = findViewById(R.id.btnVerify);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("verifying mobile no....");

        verificationStateChangedCallback =new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Mobile no verified successfully ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        };

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNumber = "+91"+etMobileNo.getText().toString();
                if (!mobileNumber.equalsIgnoreCase("")){
                    verifyMobileNo(mobileNumber);
                }else Toast.makeText(getApplicationContext(),"Please Enter Valid Mobile No.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void verifyMobileNo(String mobile){
        progressDialog.show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(mobile,60, TimeUnit.SECONDS,this,verificationStateChangedCallback);
    }
}