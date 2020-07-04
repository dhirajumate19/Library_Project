package com.example.mylibrary;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mylibrary.ui.home.HomeViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchBookSet extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirestoreRecyclerAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    private SearchView searchbook;
    private HomeViewModel homeViewModel;
    private CoordinatorLayout layout;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
   private RecyclerView recyclerView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> list1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        searchbook = findViewById(R.id.serachview);
        progressBar = findViewById(R.id.progress_bar);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        init();
        BookList();
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void BookList() {
        Query query = db.collection("Book");

        FirestoreRecyclerOptions<Book> options= new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Book,BookHolder>(options) {
            @Override
            public void onBindViewHolder(BookHolder holder, int position, Book model) {
                progressBar.setVisibility(View.GONE);
                holder.Bookid.setText(model.getId());
                holder.Booktitle.setText(model.getTitle());
                holder.Booktype.setText(model.getType());
                holder.BookAvailable.setText(model.getAvailable());

//                holder.itemView.setOnClickListener(v -> {
//                    Snackbar.make(recyclerView, model.getName()+", "+model.getTitle()+" at "+model.getCompany(), Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                });
            }

            @Override
            public BookHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.book_view, group, false);

                return new BookHolder(view);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

public class BookHolder extends RecyclerView.ViewHolder {
    TextView Bookid, Booktitle, Booktype, BookAvailable, BookTotal;

    public BookHolder(View itemView) {
        super(itemView);

             Bookid = itemView.findViewById(R.id.bookId);
           Booktitle = itemView.findViewById(R.id.bookName);
          Booktype = itemView.findViewById(R.id.bookType);
          BookAvailable = itemView.findViewById(R.id.bookAvailable);
          BookTotal = itemView.findViewById(R.id.bookTotal);
    }
}
}