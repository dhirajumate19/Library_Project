package com.example.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProcessVerifiacion<phoneAuthProvider> extends AppCompatActivity implements View.OnClickListener {
    private EditText codebox;
    private Button proceesbtn, resendmail;
    private TextView everifytxt;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
   private FirebaseUser firebaseUser;

    private PhoneAuthProvider.ForceResendingToken resendingToken;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.processverification);
        init();

        String mobile = getIntent().getStringExtra("mobile");
        Log.i("task","getting from previous  "+mobile);
        sendVerificationCode(mobile);

        email_check();


       proceesbtn.setOnClickListener(this);
        resendmail.setOnClickListener(this);

    }



    private void sendVerificationCode(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
              phoneNumber, 60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, getPhoneAuthProvider);

    }



   PhoneAuthProvider.OnVerificationStateChangedCallbacks getPhoneAuthProvider=
           new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


               @Override
       public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
           String code = phoneAuthCredential.getSmsCode();
           if (code != null) {
               codebox.setText(code);
           //   ProceesNext();
           }
       }

       @Override
       public void onVerificationFailed(FirebaseException e) {

           Log.e("task" ,"onVerificationCompleted:" + e);


                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                    codebox.setError("Invalid phone number.");

                } else if (e instanceof FirebaseTooManyRequestsException) {

                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.", Snackbar.LENGTH_SHORT).show();

                }
       }

       @Override
       public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
           super.onCodeSent(s, forceResendingToken);
           resendingToken = forceResendingToken;
       }
   };


    private void email_check() {
         firebaseUser = mAuth.getCurrentUser();
        if (!firebaseUser.isEmailVerified()) {
            everifytxt.setVisibility(View.VISIBLE);
            resendmail.setVisibility(View.VISIBLE);
            FirebaseUser fusewr = mAuth.getCurrentUser();
            fusewr.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ProcessVerifiacion.this, "Email Verification Link Send to your EMail ", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProcessVerifiacion.this, " try again some error occured" + e, Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void resend_link() {
        resendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser fusewr = mAuth.getCurrentUser();
                fusewr.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProcessVerifiacion.this, "Email Verification Link Send to your Email ", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProcessVerifiacion.this, " try again some error occured" + e, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    private void init() {
        resendmail = findViewById(R.id.Verifyemail);
        everifytxt = findViewById(R.id.Emailverifytxt);
        codebox = findViewById(R.id.editTextcode);
        proceesbtn = findViewById(R.id.proceesdbutton);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
everifytxt.setVisibility(View.INVISIBLE);
resendmail.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onClick(View view) {
        if (view == proceesbtn) {
            String code = codebox.getText().toString().trim();

            if (code.isEmpty() || code.length() < 6 ){

                codebox.setError("Enter code...");
                everifytxt.setVisibility(View.VISIBLE);
                codebox.requestFocus();
                return;
            } if (!firebaseUser.isEmailVerified()){
                everifytxt.setVisibility(View.VISIBLE);
                resendmail.setVisibility(View.VISIBLE);
            }
            ProceesNext();

        }
        if (view == resendmail) {
            resend_link();
        }
    }

    private void ProceesNext() {
        Intent intent = new Intent(ProcessVerifiacion.this, Home.class);
        startActivity(intent);
    }
}
