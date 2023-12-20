package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {

    DBHelper DB;
    EditText user, mail, pass;
    Button signup, signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        signup = findViewById(R.id.btnsignup);
        signin = findViewById(R.id.btnsignin);

        user = findViewById(R.id.usertxt);
        mail = findViewById(R.id.emailtxt);
        pass = findViewById(R.id.password);

        DB = new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail.getText().toString();
                String username = user.getText().toString();
                String password = pass.getText().toString();

                if (email.equals("")||username.equals("")||password.equals(""))
                    Toast.makeText(Signup.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuser = DB.checkusername(username);
                    if (checkuser==false){
                        Boolean insert = DB.insertData(email,username,password);
                        if (insert==true){
                            Toast.makeText(Signup.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(Signup.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(Signup.this, "User Already exists please signin", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    }
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });


    }
}