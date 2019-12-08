package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // make objects of things on registration page
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;


    private FirebaseAuth firebaseAuth;  // make an object for firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize objects

        firebaseAuth = FirebaseAuth.getInstance();

        // check if the user is signed in
        if(firebaseAuth.getCurrentUser()!= null)
        {
            // profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }


        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword =(EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        progressDialog = new ProgressDialog(this);

        // make button listeners
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }

    private void registerUser()
    {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            // check if email is empty
            Toast.makeText(this,"Dude please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Dude please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering please wait");
        progressDialog.show();



        // creates email and password on firebase of the user
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful())
                       {  // successfully registered and logged in
                           finish();
                           startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                       }
                       else
                       {
                           Toast.makeText(MainActivity.this,"You were NOT registered", Toast.LENGTH_SHORT).show();
                       }
                       progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void  onClick(View view){

        if(view == buttonRegister)
        { // register the user
            registerUser();
        }

        if(view == textViewSignin)
        { // User signs in if they have account
            startActivity(new Intent(this, Login.class));

        }
    }
}
