package com.example.user.week2daily2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class FileIO extends AppCompatActivity {

    public FileIO() {

    }

    public void write(String fileContents) {

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("edittext", fileContents);
        editor.apply();
        Toast.makeText(this, "Wrote to File!" + fileContents, Toast.LENGTH_SHORT).show();
    }

    public String read() {

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        Toast.makeText(this, "Data retrieved", Toast.LENGTH_SHORT).show();
        return sharedPreferences.getString("edittext", "Default String");
    }
}
