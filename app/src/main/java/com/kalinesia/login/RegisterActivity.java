package com.kalinesia.login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {

    EditText username, email, password, confirmPassword; //Declaration EditTexts
    TextInputLayout txtUsername, txtEmail, txtPassword, txtConfirmPassword; //Declaration TextInputLayout
    Button buttonRegister; //Declaration Button
    Cursor cursor; //Declaration Cursor
    DataHelper dataHelper; //Declaration SqliteHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dataHelper = new DataHelper(this);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

        txtUsername = (TextInputLayout) findViewById(R.id.txtUsername);
        txtEmail = (TextInputLayout) findViewById(R.id.txtEmail);
        txtPassword = (TextInputLayout) findViewById(R.id.txtPassword);
        txtConfirmPassword = (TextInputLayout) findViewById(R.id.txtConfirmPassword);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        TextView txtBacktoLogin = (TextView) findViewById(R.id.txtBacktoLogin);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String UserName = username.getText().toString();
                    String Email = email.getText().toString();
                    String Password = password.getText().toString();

                    // Query check email
                    SQLiteDatabase db = dataHelper.getReadableDatabase();
                    cursor = db.rawQuery("SELECT id FROM users WHERE username = '" + UserName + "'",null);
                    cursor.moveToFirst();
                    if (cursor.getCount()>0) {
                        //Email exists with email input provided so show error user already exist
                        Toast.makeText(getApplicationContext(), "Username already exists",
                                Toast.LENGTH_LONG).show();
                    }else{

                        SQLiteDatabase query = dataHelper.getWritableDatabase();
                        query.execSQL("insert into users(username, email, password) values('" +
                                UserName + "','" +
                                Email + "','" +
                                Password + "')");
                        Toast.makeText(getApplicationContext(), "User created successfully! Please Login",
                                Toast.LENGTH_LONG).show();

                        //User Logged in Successfully Launch You home screen activity
                        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        txtBacktoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String UserName = username.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String ConfirmPassword = confirmPassword.getText().toString();

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            txtUsername.setError("Please enter valid Username!");
        } else {
            valid = true;
            txtUsername.setError(null);
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            txtEmail.setError("Please enter valid email!");
        } else {
            valid = true;
            txtEmail.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            txtPassword.setError("Please enter valid password!");
        } else if (Password.length() < 4 ) {
            valid = false;
            txtPassword.setError("Password is to short, min 5 char!");
        } else {
            valid = true;
            txtPassword.setError(null);

        }
        //Handling validation for Confirm Password field
        if (ConfirmPassword.isEmpty() || !ConfirmPassword.equals(Password)) {
            valid = false;
            txtConfirmPassword.setError("Confirm Password is not the same!");
        } else {
            valid = true;
            txtConfirmPassword.setError(null);
        }

        return valid;
    }
}
