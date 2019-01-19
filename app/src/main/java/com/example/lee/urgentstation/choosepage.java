package com.example.lee.urgentstation;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosepage);

        assetManager = getResources().getAssets();

        hosun_station = getIntent().getExtras().getString("hosun_station");
        station = getIntent().getExtras().getString("station");
        photoView = (PhotoView)findViewById((R.id.showmap));
       Glide.with(this).load("https://www.wantae.cf/testJpgs/"+hosun_station+"역.jpg").into(photoView);


    }

    public void checktobutton(View v){

        Glide.with(this).load("https://www.wantae.cf/testJpgs/4호선_동대문역.jpg").into(photoView);
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
               station_hosun.put(splitstationname[1],strResult[0]);
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
