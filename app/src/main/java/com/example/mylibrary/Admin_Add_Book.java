package com.example.mylibrary;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Admin_Add_Book extends AppCompatActivity {

    private TextInputLayout book_title,book_id;
    private Spinner spinner1;
    private FirebaseFirestore db;
    Button add_btn;
    String type;
    ProgressDialog p1;


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__add__book);
        FirebaseApp.initializeApp(this);
        init();

        List<String> list = new ArrayList<>();
        list.add("Select Account Type");
        list.add("Computer Department");
        list.add("civil Department");
        list.add("Electrical Department");
        list.add("Mechanical Department");
        list.add("First Year Department");
        list.add("Magazines");
        list.add("other Motivational");

        ArrayAdapter adapter =new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Admin_Add_Book.this,"Select Category ",Toast.LENGTH_SHORT).show();
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("task","Clicking on button "+view);
            addBook();
            }
        });
    }
// initialize the Attribute
    private void init() {
        book_title=(TextInputLayout)findViewById(R.id.addbookTitle);
        book_id=(TextInputLayout) findViewById(R.id.addbookid);
        spinner1=(Spinner)findViewById(R.id.spinner1);
        add_btn=(Button)findViewById(R.id.addbookbtn);
        p1=new ProgressDialog(this);
        p1.setCancelable(false);
        db=FirebaseFirestore.getInstance();

    }

    // Verify Method For Title
    private boolean verifyTitle()
    {
        String title=book_title.getEditText().getText().toString().trim();
        Log.d("task","Working Title"+book_title);
        if(title.isEmpty())
        {   book_title.setErrorEnabled(true);
            book_title.setError("Title Required");
            return true;
        }
        else
        {
            book_title.setErrorEnabled(false);
            return false;
        }
    }
// Verify Method For ID
    private boolean verifyId()
    {
        String id=book_id.getEditText().getText().toString().trim();
        Log.d("task","ID working "+book_id);
        if(id.isEmpty())
        {   book_id.setErrorEnabled(true);
            book_id.setError("Book Id Required");
            return true;
        }
        else
        {
            book_id.setErrorEnabled(false);
            return false;
        }
    }

    // Verify Method For Category
    private boolean verifyCategory()
    {
        if (type.equals("Select Book Category"))
        {
            Toast.makeText(this, "Please select Book Category !", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
// method for add book
    private void addBook()
    {

        boolean res=(verifyId()|verifyTitle()|verifyCategory());
        Log.d("task","Add book working "+res);
        if(res==true)
            return;
        else
        {

            p1.setMessage("Adding Book");
            p1.show();
            final String id=book_id.getEditText().getText().toString().trim();
            int id1=Integer.parseInt(id);
            db.document("Book/"+id1).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if((task.isSuccessful())&&(task.getResult().exists()==false))
                    {

                        String id=book_id.getEditText().getText().toString().trim();
                        String title=book_title.getEditText().getText().toString().trim();
                       int id1=Integer.parseInt(id);

                         Map<String,Object> books= new HashMap<>();
                        books.put("bookid",id);
                        books.put("booktitle",title);
                       books.put("type1", type);
                        Log.d("task","book class  working "+books);
                        db.document("Book/"+id1).set(books).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {   p1.cancel();
                                    Toast.makeText(Admin_Add_Book.this, "Book Added !", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {   p1.cancel();
                                    Toast.makeText(Admin_Add_Book.this, "Try Again !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        p1.cancel();
                        Toast.makeText(Admin_Add_Book.this, "This Book is already added \n or Bad Connection !", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }
    }

   



}
