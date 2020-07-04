package com.example.mylibrary.ui.LogoutUser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mylibrary.Login;
import com.example.mylibrary.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class LogoutuserFragment extends Fragment {

    private LogoutuseerViewmodel LogoutuserViewModel;
    boolean yes=true;
    boolean no=false;

    FirebaseAuth mAuth;

    FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogoutuserViewModel = ViewModelProviders.of(this).get(LogoutuseerViewmodel.class);
        View root = inflater.inflate(R.layout.fragmant_logoutuser, container, false);
        final ImageButton logout =(ImageButton) root.findViewById(R.id.Logoutuserbtn);
        final TextView textView = root.findViewById(R.id.textView);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        LogoutuserViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setMessage("Are You Sure  That You Want Exit ")
                        .setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity().getApplicationContext(), Login.class);
                        startActivity(intent);
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alertDialog=builder.create();
                alertDialog.show();


            }
        });


        return root;
    }


}
