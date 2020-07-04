package com.example.mylibrary.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibrary.Book;
import com.example.mylibrary.MySearchBookset;
import com.example.mylibrary.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment implements OnCreateOptionMenu {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirestoreRecyclerAdapter adapter;
    private SearchView searchbook;
    private CoordinatorLayout layout;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
   private RecyclerView recyclerView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> list1;
    Query query;
    private String key = "";


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        searchbook = root.findViewById(R.id.serachview);
        progressBar = root.findViewById(R.id.progress_bar);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        recyclerView = (RecyclerView) root.findViewById(R.id.recycle);
        recyclerView.setAdapter(adapter);
        final Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbarstud);
        layout = root.findViewById(R.id.layout);
        // final Button searchbooks = root.findViewById(R.id.searchbook);
        //   final  Button   reissuebtn=root.findViewById(R.id.btnReissu);
        // init();


//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//
//
//            }
//
//        });

        // searchbooks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//        Intent intent =new Intent(getActivity().getApplicationContext(),SearchBookSet.class);
//        startActivity(intent);
        // }
        //    });

//        toolbar.inflateMenu(R.menu.top_app_bar);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//
//                switch (item.getItemId()) {
//                    case R.id.favorite: {
//                        Snackbar.make(layout, "Add To favorite", Snackbar.LENGTH_SHORT).show();
//                        Log.i("task","working fine");
//                    }
//                    case  R.id.serachview:{
//                          Intent intent=new Intent(getActivity().getApplicationContext(), SearchBookSet.class);
//                         startActivity(intent);
//
//                    }
//                    break;
//                    default:
//
//                }
//                return true;
//            }
//        });
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_app_bar,menu);
        MenuItem item=menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) MenuItemCompat.setActionView(item, (View) menu);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                SearchData(s);
                //  Log.i("task", "searchdone" + s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_action:
                Toast.makeText(getContext().getApplicationContext(), "You have selected Search Menu", Toast.LENGTH_SHORT).show();
                return true;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
    }







//    private void init() {
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(adapter);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setHasFixedSize(true);
//
//
//    }


    private void SearchData(final String s) {
        // progressDialog.setTitle("Searching....");
        // progressDialog.show();
        key = s;
        query = db.collection("Book/").whereEqualTo("booktitle", s).startAt(s).whereGreaterThan("available", 0);
        search();
        Log.i("task", "key value item" + key);
        db.collection("Book/").whereEqualTo("booktitle", s).whereGreaterThan("available", 0).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("task", "error" + e);
            }
        });


    }

    private void search() {
        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>().setQuery(query, Book.class).build();

        adapter=new MySearchBookset(options);
        Log.i("task", "call class" + adapter);
       RecyclerView recyclerView1 = null;
        recyclerView1 = (RecyclerView) recyclerView.findViewById(R.id.recycle);
        recyclerView1.setAdapter(adapter);
        Log.i("tsgh", "gyuchj" + recyclerView);
        adapter.startListening();

//        adapterSearch.startListening(); //connects to firebase collection
        adapter.notifyDataSetChanged();



        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));



      }

//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
}