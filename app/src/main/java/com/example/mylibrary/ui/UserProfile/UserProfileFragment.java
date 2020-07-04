package com.example.mylibrary.ui.UserProfile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibrary.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class UserProfileFragment extends Fragment {
    ImageView uphoto;
    FirebaseFirestore db;
FirebaseAuth mAuth;
    TextView everifytxt;
    Button  resendbutton;
private StorageReference mStorageRef;
    private UserProfileViewModel UserProfilevViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        UserProfilevViewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user_profile, container, false);
        final TextView textView = root.findViewById(R.id.txt);
        uphoto = root.findViewById(R.id.uploadimage);
        final EditText ufullname = root.findViewById(R.id.uname);
        final EditText uemail = root.findViewById(R.id.uemail);
        final EditText uphone = root.findViewById(R.id.uphone);
        final Button upchangimage = root.findViewById(R.id.UCimage);
        resendbutton = root.findViewById(R.id.Verifyemail);
        everifytxt = root.findViewById(R.id.Emailverifytxt);

        // firebase instances
        mAuth= FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();


        UserProfilevViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        // Email Verified or not
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        if (!firebaseUser.isEmailVerified()){
             everifytxt.setVisibility(View.VISIBLE);
             resendbutton.setVisibility(View.VISIBLE);
            FirebaseUser fusewr=mAuth.getCurrentUser();
            fusewr.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getContext().getApplicationContext(),"Email Verification Link Send to your EMail ",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity().getApplicationContext()," ",Toast.LENGTH_SHORT).show();

                }
            });
        }


// image code for download image

        StorageReference profiloeref= mStorageRef.child("users/"+mAuth.getCurrentUser().getUid()+"/Profile.jpg");
        profiloeref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(uphoto);

            }
        });

        db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    String group = document.getString("fname");
//                    Log.d("sample", document.getId() + " => " + document.exists() + " => " + document.getData());
//                    Log.d("data", "" + group);
//                } else {
//                    Log.w("sample2", "Error getting documents.", task.getException());
//                }
//            }
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Log.d("Obj DB","Working "+ task.isSuccessful() + " data " + task.getResult().getString("key"));
                if (task.isSuccessful() && task.getResult() != null) {
                    String ufullname = task.getResult().getString("fname");
                    String uemai = task.getResult().getString("email");
                    String uphone = task.getResult().getString("phoneno");
                    DataSetText1(ufullname);
                    DataSetText2(uemai);
                    DataSetText3(uphone);
                    //other stuff
                } else {
                    //deal wTith error
                    Toast.makeText(getContext().getApplicationContext(),"Error.."+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            private void DataSetText3(String number) {
                uphone.setText(number);
                uphone.isEnabled();
            }

            private void DataSetText2(String uemailg) {
                uemail.setText(uemailg);

            }

            private void DataSetText1(String data) {
                ufullname.setText(data);
                ufullname.isEnabled();
            }
        });
        upchangimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGI= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               startActivityForResult(openGI,1000);
            }


        });



        return root;
    }

@Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000 ){
            if(resultCode==Activity.RESULT_OK){
                Uri imageuri=data.getData();
                uphoto.setImageURI(imageuri);

                Putimage(imageuri);

            }
        }
    }

    private void Putimage(Uri imageuri) {
            // This Method uplod image on firebase Storage
        final StorageReference FR= mStorageRef.child("users/" + mAuth.getCurrentUser().getUid() + "/Profile.jpg");
        FR.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("task","Working"+ taskSnapshot);
                FR.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(uphoto);
                        Log.d("task","uploadphoto"+uphoto);
                    }
                });
                Toast.makeText(getActivity()," Image Uploaded Succesfully",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            Log.d("task",""+e);
            Toast.makeText(getActivity()," Failed To Upload Image",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
