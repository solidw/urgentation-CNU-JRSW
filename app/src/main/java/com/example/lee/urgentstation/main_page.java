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
        EditText idEdit = (EditText)findViewById(R.id.station);
        String stationname = idEdit.getText().toString();

        if(stationname.length()!=0) {
            Intent intent = new Intent(this, choosepage.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),"입력이 안되있어요!.",Toast.LENGTH_LONG).show();
        }
    }
}


