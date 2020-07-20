package com.example.mylibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class

Login extends AppCompatActivity {
    TextInputLayout email, password;
    Button btnlogin;
    TextView forgetpass, newuser;
    ProgressBar progressBarlog;

    FirebaseAuth mAuth;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (TextInputLayout) findViewById(R.id.email);
        password = (TextInputLayout) findViewById(R.id.password);

        btnlogin = (Button) findViewById(R.id.loginLog);
        forgetpass = (TextView) findViewById(R.id.forgetPassword);
        newuser = (TextView) findViewById(R.id.newuser);


        final ProgressDialog progressDialog = new ProgressDialog(this);
        //firebase instance
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();



        // button action
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("task", "button clicked" + view);

                boolean result = (verifyEmailId() | verifyPass());
                if (result == true) {
                    return;
                }
                String mail = email.getEditText().getText().toString().trim();
                String pasword = password.getEditText().getText().toString().trim();


                progressDialog.setMessage("Please Wait... Signing You in !");
                progressDialog.show();

                //sign in method
                mAuth.signInWithEmailAndPassword(mail, pasword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("tsask", "task success" + task);

                            String currentuser = mAuth.getCurrentUser().getUid();
                            Log.i("task", "currentuseremailget" + currentuser);
                            db.collection("Users").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot document = task.getResult();

                                    if (document.exists()) {
                                        Log.i("task", "studentdocumentresult" + document.getData());
                                        long group = (long) document.get("type1");
                                        Log.d("sample2", "" + group);
                                        if (group == 0) {

                                            Log.i("task", "working fine");
                                            Intent intent = new Intent(Login.this, Home.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }

                            });

                            db.collection("admin").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot document = task.getResult();

                                    if (document.exists()) {
                                        Log.i("task", "studentdocumentresult" + document.getData());
                                        long group = (long) document.get("type1");
                                        Log.d("sample2", "" + group);
                                        if (group == 1) {

                                            Log.i("task", "working fine");
                                            Intent intent = new Intent(Login.this, AdminHome.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }

                            });
                        } else {
                            Toast.makeText(Login.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "Register Here....", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ForgetPassword.class);
                startActivity(intent);
            }
        });


    }

    private boolean verifyEmailId() {
        String emailId = email.getEditText().getText().toString().trim();
        if (emailId.isEmpty()) {
            email.setErrorEnabled(true);
            email.setError(" Email ID Required");

            return true;
        }

        else {
            email.setErrorEnabled(false);
            return false;
        }
    }

    private boolean verifyPass() {
        String pass = password.getEditText().getText().toString().trim();
        if (pass.isEmpty()) {
            password.setErrorEnabled(true);
            password.setError(" Password Required");
            return true;
        }
//        else  if (pass.length()<=6){
//            password.setErrorEnabled(true);
//            password.setError(" Password Length minimun 6 character");
//            return true;
//        }
        else {
            password.setErrorEnabled(false);
            return false;
        }
    }

}
