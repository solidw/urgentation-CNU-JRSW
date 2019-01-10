package com.example.lee.urgentstation;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class main_page extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);


    }
    public void movetochoosepage(View v) {
        
        Intent intent = new Intent(this, choosepage.class);
        startActivity(intent);
    }
}


