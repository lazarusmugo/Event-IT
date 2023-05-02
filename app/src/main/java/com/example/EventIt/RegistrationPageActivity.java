package com.example.EventIt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Objects;

public class RegistrationPageActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    TextView FullName,RegUserName,RegEmail,RegPassword;
     Button btnRegister;
     TextView LabelSignUp,btnRegPasswordShow;

     Button btnLoginPage;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        FullName=findViewById(R.id.etFullName);
        RegUserName=findViewById(R.id.etRegUserName);
        RegEmail=findViewById(R.id.etRegEmail);
        RegPassword = findViewById(R.id.etRegPassword);

        btnRegister = findViewById(R.id.btnRegister);
        LabelSignUp = findViewById(R.id.LabelSignUp);
        btnLoginPage = findViewById(R.id.btnLoginPage);
        btnRegPasswordShow = findViewById(R.id.RegShowPassword2);


        auth = FirebaseAuth.getInstance();
        //hiding the action bar
        getSupportActionBar().hide();

        btnRegPasswordShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RegPassword.getText().toString().isEmpty()) {
                    RegPassword.setError("Please Enter Pass word");
                } else {
                    if (btnRegPasswordShow.getText().toString().equals("Show")) {
                        btnRegPasswordShow.setText("Hide");
                        RegPassword.setTransformationMethod(null);
                    } else {
                        btnRegPasswordShow.setText("Show");
                        RegPassword.setTransformationMethod(new PasswordTransformationMethod());
                    }
                }
            }
        });



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = RegEmail.getText().toString().trim();
                String password = RegPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    RegEmail.setError("Enter valid Email");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    RegPassword.setError("Password should be atleast length of 6!");
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }


                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegistrationPageActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegistrationPageActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegistrationPageActivity.this,"Authentication failed."+ task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Objects.requireNonNull(auth.getCurrentUser()).sendEmailVerification();
                                    Toast.makeText(RegistrationPageActivity.this,"email sent",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
















/*



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Full_Name,User_Name,E_mail,Pass_word;
                Full_Name= String.valueOf(FullName.getText());
                User_Name= String.valueOf(RegUserName.getText());
                E_mail= String.valueOf(RegEmail.getText());
                Pass_word= String.valueOf(RegPassword.getText());


                 if (!Full_Name.equals("")&& !User_Name.equals("")&&!E_mail.equals("") && !Pass_word.equals("")){


                //Start ProgressBar first (Set visibility VISIBLE)
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[4];
                        field[0] = "FullName";
                        field[1] = "UserName";
                        field[2] = "Email";
                        field[3] = "Password";

                        //Creating array for data
                        String[] data = new String[4];

                        data[0] = Full_Name;
                        data[1] = User_Name;
                        data[2] = E_mail;
                        data[3] = Pass_word;


                        PutData putData = new PutData("http://192.168.43.182/LoginRegister/signup.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                //End ProgressBar (Set visibility to GONE)
                                if(result.equals("Sign Up Success")){
                                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent =  new Intent(getApplicationContext(),HomePageActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    //String result = putData.getResult();
                                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
                                }
                            }


                        }
                        //End Write and Read data with URL
                    }
                });

            }
                 else {
                     Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                 }
            }
        });*/


        //linking the login button to the main activity page that is our login page

        btnLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationPageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}