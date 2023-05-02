package com.example.EventIt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.DisplayContext;
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

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    TextView etName,etPassword;
    Button btnLoginn,btnLogin;
     TextView tvAttemptsInfo;
    Button btnRegPage;
    TextView btnPasswordShow;
    int counter = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etName=findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword );
        btnLoginn = findViewById(R.id.btnLogin);
        btnRegPage = findViewById(R.id.btnRegPage);
        tvAttemptsInfo = findViewById(R.id.tvAttemptsInfo);
        btnPasswordShow = findViewById(R.id.loginShowPassword);
        //hiding the action bar
        getSupportActionBar().hide();

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {

            if(auth.getCurrentUser().isEmailVerified()) {
                Intent intent = new Intent(MainActivity.this, HomePageTwo.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"Please verify your email", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        }






    // password showing
        btnPasswordShow.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   if (etPassword.getText().toString().isEmpty()) {
                                                       etPassword.setError("Please Enter Pass word");
                                                   } else {
                                                       if (btnPasswordShow.getText().toString().equals("Show")) {
                                                           btnPasswordShow.setText("Hide");
                                                           etPassword.setTransformationMethod(null);
                                                       } else {
                                                           btnPasswordShow.setText("Show");
                                                           etPassword.setTransformationMethod(new PasswordTransformationMethod());
                                                       }
                                                   }
                                               }
                                           });




    /*   btnLoginn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Toast.makeText(getApplicationContext(),"Enter email address!", Toast.LENGTH_SHORT).show();
           }
       });
*/


        btnLoginn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email= etName.getText().toString();
                final String password= etPassword.getText().toString();




                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(),"Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(),"Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
//                                progressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        etPassword.setError("Minimum of six");
                                    } else {
                                        Toast.makeText(MainActivity.this,"Login Failed", Toast.LENGTH_LONG).show();
                                    }
                                    if(auth.getCurrentUser().isEmailVerified()) {
                                        Toast.makeText(MainActivity.this,"Loading", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this, HomePageTwo.class));
                                        finish();
                                    }else {
                                        Toast.makeText(MainActivity.this,"Email not verified", Toast.LENGTH_SHORT).show();
                                        FirebaseAuth.getInstance().signOut();
                                    }

                                }
                            }
                        });
            }
        });





/*

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String User_Name,Pass_word;

                User_Name= String.valueOf(etName.getText());
                Pass_word= String.valueOf(etPassword.getText());


                if (!User_Name.equals("")&&!Pass_word.equals("")){


                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];

                            field[0] = "UserName";
                            field[1] = "Password";

                            //Creating array for data
                            String[] data = new String[2];

                            data[0] = User_Name;
                            data[1] = Pass_word;


                            PutData putData = new PutData("http://192.168.43.182/LoginRegister/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    //End ProgressBar (Set visibility to GONE)
                                    if(result.equals("Login Success")){
                                        Toast.makeText(getApplicationContext(), "Log in Success", Toast.LENGTH_SHORT).show();
                                        Intent intent =  new Intent(getApplicationContext(),HomePageActivity.class);
                                        startActivity(intent);
                                        finish();


                                    }

                                    else{
                                       //String result = putData.getResult();
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplicationContext(), "Log in Failed", Toast.LENGTH_SHORT).show();
                                        //reducing counter
                                        counter --;
                                        tvAttemptsInfo.setText("No. of attempts remaining:" +counter);

                                        if(counter == 0){
                                            //btnLogin.setEnabled(false);
                                            Toast.makeText(getApplicationContext(), "Log  In Button Disabled", Toast.LENGTH_SHORT).show();

                                        }


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

        };

    });*/
        //linking the registration button to the registration page
        btnRegPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrationPageActivity.class);
                startActivity(intent);

            }
        });
    }

}
