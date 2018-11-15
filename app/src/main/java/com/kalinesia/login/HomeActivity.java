package com.kalinesia.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    
    SharedPreferences pref; //Declaration SharedPreferences
    Cursor cursor; //Declaration Cursor
    DataHelper dataHelper; //Declaration SqliteHelper
    TextView txtusername; //Declaration Textview
    Button buttonLogout; //Declaration Button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pref = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        String session_id =  pref.getString("session",null);

        dataHelper = new DataHelper(this);
        // Query check id user
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT username FROM users WHERE id = '" + session_id + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            TextView txtusername = (TextView) findViewById(R.id.txtusername);
            txtusername.setText("Selamat Datang, "+cursor.getString(0).toString());
        }

        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                //User Logged in Successfully Launch You home screen activity
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
