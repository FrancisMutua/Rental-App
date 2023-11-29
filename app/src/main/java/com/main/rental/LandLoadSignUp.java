package com.main.rental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LandLoadSignUp extends AppCompatActivity {
    TextView date_birth,your_name,passport_id,email_id,password_id,confirm_pass_id;

    Button signupbtn;

    AutoCompleteTextView designation_id;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_load_sign_up);

        context=this;
        date_birth=findViewById(R.id.date_birth);
        signupbtn=findViewById(R.id.signup_btn);
        designation_id=findViewById(R.id.designation_id);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        your_name=findViewById(R.id.your_name);
        passport_id=findViewById(R.id.passport_id);
        email_id=findViewById(R.id.email_id);
        password_id=findViewById(R.id.password_id);
        confirm_pass_id=findViewById(R.id.confirm_pass_id);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(LandLoadSignUp.this, R.layout.custom_spinner_item, getYourDataList());
                adapter.setDropDownViewResource(R.layout.custom_spinner_item);
                designation_id.setAdapter(adapter);

                // Show the Spinner dropdown
                designation_id.performClick();


        designation_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Update the EditText with the selected item
                Log.e("selected value:"+getYourDataList().get(position),"selected value"+getYourDataList().get(position));
                designation_id.setText(getYourDataList().get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
                //Log.e("nothing selected","nothing selected");
            }
        });

        mAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getInstance().getReference("users");
        date_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int year = 2023; // Set your initial year
                int month = 00; // Set your initial month (0-based, e.g., 0 for January)
               int day = 01; // Set your initial day

                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                // Set the selected date in the TextView
                                date_birth.setText(selectedYear + "/" + (selectedMonth + 1) + "/" + selectedDay);
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(your_name.getText().toString())) {
                    your_name.setError("Please enter an email");
                    your_name.requestFocus();
                }else if (TextUtils.isEmpty(designation_id.getText().toString())) {
                    designation_id.setError("Please enter a password");
                    designation_id.requestFocus();
                }
                else if (TextUtils.isEmpty(date_birth.getText().toString())) {
                    date_birth.setError("Please enter a password");
                    date_birth.requestFocus();
                 }
                 else if (TextUtils.isEmpty(passport_id.getText().toString())) {
                    passport_id.setError("Please enter a password");
                    passport_id.requestFocus();
                 }
                else if (TextUtils.isEmpty(confirm_pass_id.getText().toString())) {
                    confirm_pass_id.setError("Please enter a password");
                    confirm_pass_id.requestFocus();
                }
                else if (confirm_pass_id.getText().equals(passport_id.getText())) {
                    confirm_pass_id.setError("Please ensure the passwords are same");
                    confirm_pass_id.requestFocus();
                }
                else {
                    showPopupDialog();

                }

            }
        });

    }
    private void showPopupDialog() {
        // Create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate the custom dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.terms_conditions_layout, null);

        // Set the custom view for the dialog
        builder.setView(dialogView);

        Button cancel=dialogView.findViewById(R.id.cancel_id_terms);
        Button continue_id=dialogView.findViewById(R.id.continue_id_terms);
        continue_id.setClickable(false);
        RadioButton agreebtn=dialogView.findViewById(R.id.agree);
        if (agreebtn.isChecked()){
            continue_id.setClickable(true);
        }
        AlertDialog dialog = builder.create();
        continue_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sigUpLandLoad();
                dialog.cancel();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }


    private List<String> getYourDataList() {
        Log.e("getDatalist", "started");

        // Replace this with your actual data source
        List<String> data = new ArrayList<>();
        data.add("Mr.");
        data.add("Mrs");
        data.add("Miss");
        data.add("Ms");
        return data;
    }

    private void sigUpLandLoad() {
        // Retrieve user input from the UI elements
        String dateOfBirth = date_birth.getText().toString();
        String name = your_name.getText().toString();
        String passport= passport_id.getText().toString();
        String email = email_id.getText().toString();
        String password = password_id.getText().toString();
        String designation = designation_id.getText().toString();

        // Create a User object
        LandLoad landLoad = new LandLoad();
        landLoad.setDateOfBirth(dateOfBirth);
        landLoad.setYourName(name);
        landLoad.setPassportId(passport);
        landLoad.setEmail(email);
        landLoad.setPassword(password);
        landLoad.setDesignation(designation);

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("please wait...");
        progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.spinner));
        progressDialog.show();
        // Use Firebase Authentication to create the user
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User created successfully
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            progressDialog.dismiss();
                            // Save user details to the Realtime Database
                            databaseReference.child(firebaseUser.getUid()).setValue(landLoad).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LandLoadSignUp.this,SignIn.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context,"error",Toast.LENGTH_SHORT).show();

                                }
                            });

                            // Optionally, you can perform additional actions after user creation
                        }
                    }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                if (e.getMessage().contains("network"))
                    Toast.makeText(context,"please check your network",Toast.LENGTH_SHORT).show();
                if (e.getMessage().contains("6"))
                    Toast.makeText(context,"password should be more than six characters",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context,"Error Occurred",Toast.LENGTH_SHORT).show();

            }
        });
    }
}