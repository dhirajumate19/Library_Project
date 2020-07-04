package com.example.mylibrary;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RemoveBook extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout removebookid;
    private Button removebtn;
    private ProgressDialog progressDialog;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    Book b1;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.removebook);
        removebookid = (TextInputLayout) findViewById(R.id.Removebookid);
        removebtn = (Button) findViewById(R.id.Removebtn);
        progressDialog=new ProgressDialog(RemoveBook.this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        removebtn.setOnClickListener(this);
        b1 = new Book();
    }


    @Override
    public void onClick(View view) {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        if (view == removebtn) {
            if (removebookid.getEditText().getText().toString().trim().isEmpty()) {
                progressDialog.cancel();
                removebookid.setError("Book Id Required");
                removebookid.setErrorEnabled(true);
                return;
            }
//        Toast.makeText(RemoveBook.this,"done",Toast.LENGTH_SHORT).show();
            String id1 = removebookid.getEditText().getText().toString().trim();
            db.document("Book/"+id1).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
//                        DocumentSnapshot documentSnapshot1=task.getResult();
//                        Long id = (Long)documentSnapshot1.get("bookid");
                        Log.i("task","task work"+task);

                        if (task.getResult().exists()) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(RemoveBook.this);
                            DocumentSnapshot documentSnapshot = task.getResult();
                            Log.i("task", "get  result " + documentSnapshot);
                            String temp = "Title : " + documentSnapshot.get("booktitle") + "\nCategory : " +documentSnapshot.get("type1");
                            Log.i("task", "title and category working " + temp);
                            progressDialog.cancel();
                            alertDialog.setMessage(temp).setTitle("Please Confirm").setCancelable(false).setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    progressDialog.setMessage("Removing...");
                                    progressDialog.show();
                                    if (b1.getAvailable() == b1.getTotal()) {
                                        String id = removebookid.getEditText().getText().toString().trim();
                                        db.document("Book/" + id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.cancel();
                                                Toast.makeText(RemoveBook.this, "Book Removed", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.cancel();
                                                Toast.makeText(RemoveBook.this, "Try Again !", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        progressDialog.cancel();
                                        Toast.makeText(RemoveBook.this, "This Book Is Already Issued to Student ! \n nReturn before Removing this Book", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();

                                }
                            });
                            AlertDialog alertDialog1=alertDialog.create();
                            alertDialog1.show();
                        }else {
                            progressDialog.cancel();
                            Toast.makeText(RemoveBook.this, "No Such Book Found ! \n Enter Proper Detail ", Toast.LENGTH_SHORT).show();

                        }
                    }else {
                        progressDialog.cancel();
                        Toast.makeText(RemoveBook.this, "Oops Please  Try Again !!", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }
    }
}