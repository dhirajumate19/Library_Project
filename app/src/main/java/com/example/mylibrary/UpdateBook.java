package com.example.mylibrary;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateBook extends AppCompatActivity {
    TextInputLayout book_id, book_title;
    Button update_btn;
    ProgressDialog p1;
    FirebaseFirestore db;
    private Object Tag;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);
        init();
        p1 = new ProgressDialog(this);
        p1.setCancelable(false);
        db = FirebaseFirestore.getInstance();
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update();
            }
        });


    }

    public boolean verifyId() {
        String Id = book_id.getEditText().toString().trim();
        Log.d("task", "Id getting" + Id);
        if (Id.isEmpty()) {
            book_id.setErrorEnabled(true);
            book_id.setError("Id Required");
            return true;
        } else {
            book_id.setErrorEnabled(false);
            return false;
        }

    }

    public boolean verifyTitle() {
        String title = book_title.getEditText().toString().trim();
        Log.d("task", "title getting" + title);

        if (title.isEmpty()) {
            book_title.setErrorEnabled(true);
            book_title.setError("Title Required");
            return true;
        } else {
            book_title.setErrorEnabled(false);
            return false;
        }
    }

    private void init() {
        book_id = findViewById(R.id.updatebookid);
        book_title = findViewById(R.id.updatebookTitle);
        update_btn = findViewById(R.id.buttonupdate);
    }

    public void Update() {
        boolean result = (verifyId() || verifyTitle());
        if (result == true) {
            return;
        } else {
            p1.setMessage("Updating Book....");
            p1.show();
            final String id = book_id.getEditText().getText().toString().trim();
            final int id1 = Integer.parseInt(id);

            String t = book_title.getEditText().getText().toString().trim();

            db.document("Book/" + id1).update("booktitle", t).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.i("task","result Task"+task);
                        p1.cancel();
                        Toast.makeText(UpdateBook.this, "Updated Successfully !", Toast.LENGTH_SHORT).show();
                    } else {
                        p1.cancel();
                        Toast.makeText(UpdateBook.this, "No Such Book !", Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UpdateBook.this, "Try Again !" + e, Toast.LENGTH_SHORT).show();

                }
            });

        }

    }
}
