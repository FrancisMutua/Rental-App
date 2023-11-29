package com.main.rental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    EditText email_id,password_id;
    Button signin_btn;
    FirebaseAuth firebaseAuth;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email_id=findViewById(R.id.email_id);
        context=this;
        password_id=findViewById(R.id.password_id);
        signin_btn=findViewById(R.id.signin_btn);
        firebaseAuth=FirebaseAuth.getInstance();

        TextView textView = findViewById(R.id.notregistered_id_patient);
        String text = "Not yet registered? Register here";
        SpannableString spannableString = new SpannableString(text);
        int color = ContextCompat.getColor(this, R.color.navy_blue);

// Define the start and end indices of the text to be colored
        int startIndex = text.indexOf("Register here");
        int endIndex = startIndex + "Register here".length();

// Apply the blue color to the specified text range
        spannableString.setSpan(color, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

// Create a ClickableSpan to handle the click action
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                showPopupDialog();
            }

        };

// Apply the ClickableSpan to the specified text range
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

// Set the text and `make` it clickable
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        TextView textView1 = findViewById(R.id.forgot_password_id);
        String text1 = "Forgot your password? Reset";
        SpannableString spannableString1 = new SpannableString(text1);
        int color1 = ContextCompat.getColor(this, R.color.navy_blue);

// Define the start and end indices of the text to be colored
        int startIndex1 = text1.indexOf("Reset");
        int endIndex1 = startIndex + "Reset".length();

// Apply the blue color to the specified text range
        spannableString1.setSpan(color1, startIndex1, endIndex1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                String email = email_id.getText().toString();
                // Validate email
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    // Send a password reset email
                    firebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Password reset email sent successfully
                                        Toast.makeText(SignIn.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Handle password reset email sending failure
                                        Toast.makeText(SignIn.this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    // Show an error message to the user
                    Toast.makeText(SignIn.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                }
            }
        };

// Apply the ClickableSpan to the specified text range
        spannableString1.setSpan(clickableSpan1, startIndex1, endIndex1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

// Set the text and `make` it clickable
        textView1.setText(spannableString1);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email_id.getText())){
                    email_id.setError("please enter email");
                    email_id.requestFocus();
                }
                else if(TextUtils.isEmpty(password_id.getText())){
                    password_id.setError("please enter password");
                    password_id.requestFocus();
                }
                else {
                    loginAsLandload(email_id.getText().toString(),password_id.getText().toString());
                }

            }


        });
    }
    private void showPopupDialog() {
        // Create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate the custom dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.sign_up_popup, null);

        // Set the custom view for the dialog
        builder.setView(dialogView);

        Button cancel=dialogView.findViewById(R.id.cancel_id);
        Button continue_id=dialogView.findViewById(R.id.continue_id);

        continue_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this,LandLoadSignUp.class));
            }
        });
        AlertDialog dialog = builder.create();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
    private void loginAsLandload(String email, String password) {
        firebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Proceed to LandLoadHome activity
                            startActivity(new Intent(SignIn.this, LandLoadHome.class));
                            finish();
                        } else {

                            Toast.makeText(SignIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
        });
    }


}