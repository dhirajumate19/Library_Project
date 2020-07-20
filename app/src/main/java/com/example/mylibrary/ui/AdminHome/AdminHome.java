package com.example.mylibrary.ui.AdminHome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mylibrary.AdminCollectFine;
import com.example.mylibrary.Admin_Add_Book;
import com.example.mylibrary.R;
import com.example.mylibrary.RemoveBook;
import com.example.mylibrary.UpdateBook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class AdminHome extends Fragment implements View.OnClickListener {
    private AdminHomeViewModel AdminHomeViewModel;
private ImageButton addbooks,updated,removebooks,collectfined,issuedtousers,returnfoemusers,reissuedbooks;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AdminHomeViewModel = ViewModelProviders.of(this).get(AdminHomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_admin_home, container, false);
        Log.i("task","work"+AdminHomeViewModel+"work"+root);
      //  ID Reference

        final TextView textView = root.findViewById(R.id.text_Home);
        addbooks =root.findViewById(R.id.adddbok);
        removebooks = root.findViewById(R.id.removebook);
        updated = root.findViewById(R.id.update);
        collectfined = root.findViewById(R.id.collectfine);
        issuedtousers = root.findViewById(R.id.issuedbooktouser);
        returnfoemusers = root.findViewById(R.id.returnfromuser);
        reissuedbooks = root.findViewById(R.id.reissuedbook);


        AdminHomeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
            // Listener On Button
        addbooks.setOnClickListener(this);
        removebooks.setOnClickListener(this);
        updated.setOnClickListener(this);
        collectfined.setOnClickListener(this);
        issuedtousers.setOnClickListener(this);
        returnfoemusers.setOnClickListener(this);
        reissuedbooks.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view == addbooks){
            // Go to Add Book Class
            intent = new  Intent(getActivity().getApplicationContext(), Admin_Add_Book.class);
            startActivity(intent);
        }
        if (view==removebooks){

            // Go to Remove  Book Class
                startActivity(new Intent(getActivity().getApplicationContext(), RemoveBook.class));


        }
        if (view==updated){

            // Go to Update Book Class
               startActivity(new Intent(getActivity().getApplicationContext(), UpdateBook.class));


        }
        if (view==collectfined){


               startActivity(new Intent(getActivity().getApplicationContext(), AdminCollectFine.class));


        }
        if (view==issuedtousers){


//                startActivity(new Intent(getActivity().getApplicationContext(), IssuedTouser.class));
}

        if (view==returnfoemusers){

            {
//                startActivity(new Intent(getActivity().getApplicationContext(), ReturnfromUser.class));
//
            }
        }
        if (view==reissuedbooks){

            {
//                startActivity(new Intent(getActivity().getApplicationContext(), ReissuedBook.class));
//
            }
        }


    }
}
