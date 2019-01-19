package com.example.lee.urgentstation;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;


public class main_page extends AppCompatActivity {
    String stationname;
    HashMap<String,String> name_hosunname = new HashMap<>();
    AssetManager assetManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        assetManager = getResources().getAssets();

    }
    public void movetochoosepage(View v) {
        EditText idEdit = (EditText)findViewById(R.id.station);
        stationname = idEdit.getText().toString();
        getDataFromAsset();
        if(stationname.length()!=0) {
            Intent intent = new Intent(this, choosepage.class);
            intent.putExtra("hosun_station",name_hosunname.get(stationname));
            intent.putExtra("station",stationname);
           // Toast.makeText(getApplicationContext(),name_hosunname.get(stationname),Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),"입력이 안되있어요!.",Toast.LENGTH_LONG).show();
        }
    }

    public void getDataFromAsset(){
        InputStream inputStream = null;

        try{
            //asset manager에게서 inputstream 가져오기
            inputStream = assetManager.open("call.txt");

            //문자로 읽어들이기
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            //파일읽기
            String[] strResult = null;
            String line = null;
            while((line=reader.readLine()) != null){
                strResult = line.split(":");
                String[] splitstationname = strResult[0].split("_");
                name_hosunname.put(splitstationname[1],strResult[0]);

            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try { inputStream.close(); } catch (IOException e) {}
            }
        }
    }
}


