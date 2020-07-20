package com.example.mylibrary;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class Registration extends AppCompatActivity implements View.OnClickListener {
    public static String TAG;

    // Text Input Layout
    TextInputLayout Fullname, Email, Phone, Password, enrollno, cardno;
    Button btnRegister;
    TextView txtlink;

    private Spinner userType;
    private CheckBox check1;

    private ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String UserId;
    private PhoneAuthProvider.ForceResendingToken resendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks phoneAuthProvider;
    private String verificationId;
    private String type;
    private int type1;
    private int temp;
    private boolean mVerificationInProgress = false;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Initialize();

        ///   User type selection
        List<String> list = new ArrayList<>();
        list.add("Select Account Type");
        list.add("User");
        list.add("Admin");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(adapter);
        userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).toString().equals("Select Account Type")) {
                    type = parent.getItemAtPosition(position).toString();
                    // editPass1.setEnabled(false);
                    Password.setEnabled(false);
                    Fullname.setEnabled(false);
                    Email.setEnabled(false);
                    enrollno.setEnabled(false);
                    cardno.setEnabled(false);

                    cardno.setErrorEnabled(false);
                    enrollno.setErrorEnabled(false);
                    Email.setErrorEnabled(false);
                    Fullname.setErrorEnabled(false);
                    Password.setErrorEnabled(false);
                    //  editPass1.setErrorEnabled(false);

                }
                // check for user
                else if (parent.getItemAtPosition(position).toString().equals("User")) {
                    type = parent.getItemAtPosition(position).toString();
                    // editPass1.setEnabled(true);
                    Password.setEnabled(true);
                    Fullname.setEnabled(true);
                    Email.setEnabled(true);
                    enrollno.setEnabled(true);
                    cardno.setEnabled(true);

                    cardno.setErrorEnabled(false);
                    enrollno.setErrorEnabled(false);
                    Email.setErrorEnabled(false);
                    Fullname.setErrorEnabled(false);
                    Password.setErrorEnabled(false);
                    //  editPass1.setErrorEnabled(false);
                } else {
                    type = parent.getItemAtPosition(position).toString();
                    //   editPass1.setEnabled(true);
                    Password.setEnabled(true);
                    Fullname.setEnabled(true);
                    Email.setEnabled(true);
                    enrollno.setEnabled(false);
                    cardno.setEnabled(false);

                    cardno.setErrorEnabled(false);
                    enrollno.setErrorEnabled(false);
                    Email.setErrorEnabled(false);
                    Fullname.setErrorEnabled(false);
                    Password.setErrorEnabled(false);
                    //   editPass1.setErrorEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnRegister.setOnClickListener(this);
        // check1.setOnClickListener((View.OnClickListener) this);

        txtlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Registration.this, "Login here", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onClick(View view) {

        if (view == btnRegister) {
            String phonenumber = Phone.getEditText().getText().toString().trim();
//            sendVerificationCod(phonenumber);
//            phoneauth();
            //  check1.setChecked(true);
            //    btnRegister.setEnabled(true);
            register();

        }
    }
 // register method user and admin
    private void register() {
        if (verifyType()) return;

        if (type.equals("User")) {
            boolean res = (verifyName() | verifyCardNo() | verifyEmailId() | verifyEnrollNo() | verifyPass() | verifyphonenumber());
            if (res == true) return;
        }
        if (type.equals("Admin")) {
            boolean res = (verifyName() | verifyEmailId() | verifyPass());
            if (res == true) return;
        }

        String mail = Email.getEditText().getText().toString().trim();
        String pasword = Password.getEditText().getText().toString().trim();
        if (type.equals("User")) {
            type1 = 0;
        } else {
            type1 = 1;
        }


        progressDialog.setMessage("Registering User ... ");
        progressDialog.show();


        mAuth.createUserWithEmailAndPassword(mail, pasword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {


                //  vsend link to mail


//                FirebaseUser fusewr = mAuth.getCurrentUser();
//                fusewr.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(Registration.this, "Email Verification Link Send to your EMail ", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(Registration.this, " ", Toast.LENGTH_SHORT).show();
//
//                    }
//                });


                if (task.isSuccessful()) {
                    final String currentUser = mAuth.getCurrentUser().getUid();
                    Log.d("task", "" + currentUser);


                    // User registration block
                    if (type1 == 0) {
                        String id = Email.getEditText().getText().toString().trim();
                        String name = Fullname.getEditText().getText().toString().trim();
                        int enroll = Integer.parseInt(enrollno.getEditText().getText().toString().trim());
                        String card = cardno.getEditText().getText().toString().trim();
                        String pnumber = Phone.getEditText().getText().toString().trim();


                        Map<String, Object> user = new HashMap<>();
                        user.put("fname", name);
                        user.put("email", id);
                        user.put("phoneno", pnumber);
                        user.put("enroll", enroll);
                        user.put("cardno", card);
                        user.put("type1", type1);
                        db.collection("Users").document(mAuth.getCurrentUser().getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.i("message", "DocumentSnapshot added with ID: " + mAuth.getCurrentUser().getUid());
                                Toast.makeText(Registration.this, "Account Created...", Toast.LENGTH_SHORT).show();
                                String phonenumber = Phone.getEditText().getText().toString().trim();
                               String locale = getApplication().getApplicationContext().getResources().getConfiguration().locale.getCountry();
                               Log.i("task","code"+locale);
                                if (phonenumber.isEmpty() || phonenumber.length() < 10) {
                                    Phone.setError("Valid number is required");
                                    Phone.requestFocus();
                                    return;
                                }
                                String mobile=phonenumber+locale;
                                Log.i("task","with code"+mobile);
                                Intent intent = new Intent(Registration.this, ProcessVerifiacion.class);
                                intent.putExtra("mobile",mobile);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Registration.this, "Please Try Again !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    // Admin registration block

                    else {
                        String id = Email.getEditText().getText().toString().trim();
                        String name = Fullname.getEditText().getText().toString().trim();
                        String pnumber = Phone.getEditText().getText().toString().trim();

                        Map<String, Object> Admin = new HashMap<>();
                        Admin.put("fname", name);
                        Admin.put("email", id);
                        Admin.put("phoneno", pnumber);
                        Admin.put("type1", type1);

                        db.collection("admin").document(mAuth.getCurrentUser().getUid()).set(Admin).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.cancel();
                                Toast.makeText(Registration.this, "Registered Successfully.... !", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), AdminHome.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                AuthCredential authCredential = EmailAuthProvider.getCredential("Email", "Password");
                                Log.d("task", "working" + authCredential);
                                firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        //Use Delete method to delete user
                                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.w(TAG, "Error While adding document" + task);
                                                }
                                            }
                                        });
                                    }
                                });
                                Toast.makeText(Registration.this, "Please Try Again !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    progressDialog.cancel();
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(Registration.this, "Already Registered ! ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Registration.this, "Registration Failed ! Try Again " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    /// initialize Layout
    private void Initialize() {

        setContentView(R.layout.activity_registration);

        Fullname = (TextInputLayout) findViewById(R.id.name);
        Email = (TextInputLayout) findViewById(R.id.email);
        Phone = (TextInputLayout) findViewById(R.id.uphone);
        Password = (TextInputLayout) findViewById(R.id.password);
        enrollno = (TextInputLayout) findViewById(R.id.editEnrollNo);
        cardno = (TextInputLayout) findViewById(R.id.cardno);
        btnRegister = (Button) findViewById(R.id.button);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        check1 = (CheckBox) findViewById(R.id.check1);
        userType = (Spinner) findViewById(R.id.type);


        txtlink = (TextView) findViewById(R.id.txtlog);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    // method for verify Name
    private boolean verifyName() {

        String name = Fullname.getEditText().getText().toString().trim();
        if (name.isEmpty()) {
            Fullname.setErrorEnabled(true);
            Fullname.setError("Name Required");
            return true;
        } else {
            Fullname.setErrorEnabled(false);
            return false;
        }
    }

    // method for verify card no
    private boolean verifyCardNo() {
        String cardNo = cardno.getEditText().getText().toString().trim();
        if (cardNo.isEmpty()) {
            cardno.setErrorEnabled(true);
            cardno.setError("Card No. Required");
            return true;
        } else {
            cardno.setErrorEnabled(false);
            return false;
        }
    }

    // method for verify phone number
    private boolean verifyphonenumber() {

        String phonenumber = Phone.getEditText().getText().toString().trim();
        if (phonenumber.isEmpty() | phonenumber.length() != 13) {

            Phone.setErrorEnabled(true);
            Phone.setError("Phone No. Valid Required"  );
            return true;
        } else {
            Phone.setErrorEnabled(false);
            return false;
        }
    }

//    private void sendVerificationCod(String phonenumber) {
//        PhoneAuthProvider.getInstance()
//                        .verifyPhoneNumber("+91" + phonenumber,
//                        60, TimeUnit.SECONDS,
//                        TaskExecutors.MAIN_THREAD,
//                        phoneAuthProvider);
//                 }
//
//    private void phoneauth() {
//
// phoneAuthProvider = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
////            @Override
////            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
////                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
////                mVerificationInProgress = false;
////
////                if (mVerificationInProgress && verifyphonenumber()) {
////                    return;
////                }
////            }
////
////            @Override
////            public void onVerificationFailed(FirebaseException e) {
////                Log.w(TAG, "onVerificationCompleted:" + e);
////                mVerificationInProgress = false;
////                // [END_EXCLUDE]
////
////                if (e instanceof FirebaseAuthInvalidCredentialsException) {
////                    // Invalid request
////                    // [START_EXCLUDE]
////                    Phone.setError("Invalid phone number.");
////                    // [END_EXCLUDE]
////                } else if (e instanceof FirebaseTooManyRequestsException) {
////                    // The SMS quota for the project has been exceeded
////                    // [START_EXCLUDE]
////                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.", Snackbar.LENGTH_SHORT).show();
////                    // [END_EXCLUDE]
////                }
////            }
////
////            @Override
////            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
////                super.onCodeSent(s, forceResendingToken);
////                Log.d(TAG, "onCodeSent:" + s);
////                verificationId = s;
////                resendingToken = forceResendingToken;
////            }
////        };
//    }
//    private void startPhoneNumberVerification(String toString) {
//        Log.d(TAG, "verifystart:" + toString);
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                toString,        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                this,               // Activity (for callback binding)
//               phoneAuthProvider);        // OnVerificationStateChangedCallbacks
//
//
//        mVerificationInProgress = true;
//    }

    // method for verify Enrollno
    private boolean verifyEnrollNo() {
        String enrollNo = enrollno.getEditText().getText().toString().trim();
        if (enrollNo.isEmpty()) {
            enrollno.setErrorEnabled(true);
            enrollno.setError("Enrollment No. Required");
            return true;
        } else {
            enrollno.setErrorEnabled(false);
            return false;
        }
    }
    // method for verify mailID
    private boolean verifyEmailId() {
        String emailId = Email.getEditText().getText().toString().trim();
        if (emailId.isEmpty()) {
            Email.setErrorEnabled(true);
            Email.setError(" Email ID Required");
            return true;
        }
//        else if(!emailId.endsWith("@iiitnr.edu.in"))
//        {
//            Email.setErrorEnabled(true);
//            Email.setError(" Enter Valid Email ID");
//            return true;
//        }
        else {
            Email.setErrorEnabled(false);
            return false;
        }
    }
    // method for verify Pass
    private boolean verifyPass() {
        String pass = Password.getEditText().getText().toString().trim();
        if (pass.isEmpty()) {
            Password.setErrorEnabled(true);
            Password.setError(" Password Required");
            return true;
        } else if (!(pass.length() >= 6)) {
            Password.setErrorEnabled(true);
            Password.setError("Password Length minimun 6 character");
            return true;
        } else {
            Password.setErrorEnabled(false);
            return false;
        }
    }
  // method for verify type
    private boolean verifyType() {
        if (type.equals("Select Account Type")) {
            Toast.makeText(this, "Please select account type !", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean verifyCard1() {
        db.collection("user").whereEqualTo("card", Integer.parseInt(cardno.getEditText().getText().toString().trim())).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    temp = task.getResult().size();
                }
            }
        });
        if (temp == 0) return false;
        else return true;

    }
}
