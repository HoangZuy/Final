package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText username = findViewById(R.id.user);
        final EditText password = findViewById(R.id.pass);
        Button signin = findViewById(R.id.login);
        Button signup = findViewById(R.id.signup);
        final DBHelper DB = new DBHelper(this);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals(""))
                    Toast.makeText(Login.this, "Please enter username", Toast.LENGTH_SHORT).show();
                else if (pass.equals(""))
                Toast.makeText(Login.this, "Please enter password", Toast.LENGTH_SHORT).show();
                else{
                    boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if (checkuserpass){
                        Toast.makeText(Login.this, "LogIn Successful", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(getApplicationContext(), welcomePage.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are you sure you want to Exit?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
}