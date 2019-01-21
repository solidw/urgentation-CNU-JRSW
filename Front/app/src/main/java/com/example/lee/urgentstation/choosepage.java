package com.example.lee.urgentstation;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import uk.co.senab.photoview.PhotoView;

public class choosepage extends AppCompatActivity {

    HashMap<String,String> station_hosun = new HashMap<>();
    HashMap<String,String> station_call = new HashMap<>();
    AssetManager assetManager;
    String hosun_station;
    String station;
    PhotoView photoView;
    private TextView Show_stationname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosepage);

        assetManager = getResources().getAssets();
        Show_stationname = (TextView)findViewById(R.id.show_stationname);
        hosun_station = getIntent().getExtras().getString("hosun_station");
        station = getIntent().getExtras().getString("station");
        Show_stationname.setText(station+"역");
        photoView = (PhotoView)findViewById((R.id.showmap));
       Glide.with(this).load("https://www.wantae.cf/testJpgs/"+hosun_station+"역.jpg").error(R.mipmap.no_map).into(photoView);


    }

    public void showfireextinguisher(View v){
        getDataFromAsset();
        Glide.with(this).load("https://www.wantae.cf/testJpgs/"+hosun_station+"역_소화기.jpg").error(R.mipmap.no_map).into(photoView);
    }

    public void calltostation(View v){
        getDataFromAsset();
        String tel = "tel:" +station_call.get(station);
        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse(tel));
        startActivity(intent);

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
                if(splitstationname.length==2){
               station_hosun.put(splitstationname[1],strResult[0]);}
               else{
                    splitstationname[1] = splitstationname[1]+"_"+splitstationname[2];
                    station_hosun.put(splitstationname[1],strResult[0]);
                }
                String[] callnumber = strResult[1].split("-");
                String callnumber_final = "";
              for(int i= 0; i<callnumber.length; i++){
                  callnumber_final = callnumber_final+callnumber[i];
              }
               station_call.put(splitstationname[1],callnumber_final);

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
