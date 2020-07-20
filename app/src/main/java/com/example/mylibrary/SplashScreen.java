package com.example.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private int a = 10;
    private int b = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        final CollectionReference user = db.collection("Users");
        CollectionReference admin = db.collection("admin");

        new Handler().post(new Runnable() {
            @Override
            public void run() {


                if (mAuth.getCurrentUser() != null) {


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
                                    Intent intent = new Intent(SplashScreen.this, Home.class);
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
                                    Intent intent = new Intent(SplashScreen.this, AdminHome.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }

                    });

                } else {
                    Intent i = new Intent(SplashScreen.this, Registration.class);
                    startActivity(i);
                    finish();
                }
            }
        });

    }
}