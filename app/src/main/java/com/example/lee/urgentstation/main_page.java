package com.example.lee.urgentstation;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    private TextView txtResult;
    private Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        assetManager = getResources().getAssets();
        txtResult = (TextView)findViewById(R.id.textview);
        button1 = (Button)findViewById(R.id.neareststation);

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions( main_page.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                            0 );
                }
                else{
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    String provider = location.getProvider();
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    double altitude = location.getAltitude();

                    txtResult.setText("위치정보 : " + provider + "\n" +
                            "위도 : " + longitude + "\n" +
                            "경도 : " + latitude + "\n" +
                            "고도  : " + altitude);

                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            1000,
                            1,
                            gpsLocationListener);
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1000,
                            1,
                            gpsLocationListener);
                }
            }
        });
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
    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();

            txtResult.setText("위치정보 : " + provider + "\n" +
                    "위도 : " + longitude + "\n" +
                    "경도 : " + latitude + "\n" +
                    "고도  : " + altitude);

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };




}


