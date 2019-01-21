package com.example.lee.urgentstation;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;

public class main_page extends AppCompatActivity {
    String stationname;
    HashMap<String, String> name_hosunname = new HashMap<>();
    AssetManager assetManager;
    private TextView Show;
    private Button nearestbutton;
    private List<String> station_list;
    String msg;
    AutoCompleteTextView autoCompleteTextView;
    private static final String TAG = "main_page";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);


        backThread gettoken = new backThread();
        gettoken.setDaemon(true);
        gettoken.start();


        station_list = new ArrayList<>();

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, station_list));

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                runFirstComplete();
            }
        });
        assetManager = getResources().getAssets();

        SoundSearch a = new SoundSearch();

        nearestbutton = (Button) findViewById(R.id.neareststation);
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        nearestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(main_page.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            0);
                } else {
                    getDataFromAsset();
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    String provider = location.getProvider();
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                   /*double longitude = 126.937012;
                    double latitude = 37.555445;*/
                    //double altitude = location.getAltitude();
                    String min_name = null;

                    String loadjson = loadJSONFromAsset("finalJson1to8.json");
                    try {
                        JSONArray jarray = new JSONArray(loadjson);   // JSONArray 생성
                        double min = 1000;
                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
                            String name = jObject.getString("name");
                            double json_x = jObject.getDouble("x");
                            double json_y = jObject.getDouble("y");
                            double result = calculator(latitude, longitude, json_x, json_y);
                            if (result < min) {
                                min_name = name;
                                min = result;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(main_page.this, choosepage.class);
                    intent.putExtra("hosun_station", name_hosunname.get(min_name));
                    intent.putExtra("station", min_name);
                    startActivity(intent);

                    /*txtResult.setText("위치정보 : " + provider + "\n" +
                            "위도 : " + longitude + "\n" +
                            "경도 : " + latitude + "\n" +
                            "고도  : " + altitude);*/

                   /* lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            1000,
                            1,
                            gpsLocationListener);
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1000,
                            1,
                            gpsLocationListener);*/
                }
            }
        });
    }

    public void movetochoosepage(View v) {
        stationname = ((TextView) autoCompleteTextView).getText().toString();
        //stationname = idEdit.getText().toString();
        getDataFromAsset();
        if (name_hosunname.containsKey(stationname)) {
            Intent intent = new Intent(this, choosepage.class);
            intent.putExtra("hosun_station", name_hosunname.get(stationname));
            intent.putExtra("station", stationname);
            // Toast.makeText(getApplicationContext(),name_hosunname.get(stationname),Toast.LENGTH_LONG).show();
            startActivity(intent);
        } else if (!name_hosunname.containsKey(stationname)) {
            Toast.makeText(getApplicationContext(), "해당역을 찾을수가 없어요!.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "입력이 안되있어요!.", Toast.LENGTH_LONG).show();
        }
    }

    public void getDataFromAsset() {
        InputStream inputStream = null;

        try {
            //asset manager에게서 inputstream 가져오기
            inputStream = assetManager.open("call.txt");

            //문자로 읽어들이기
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            //파일읽기
            String[] strResult = null;
            String line = null;
            while ((line = reader.readLine()) != null) {
                strResult = line.split(":");
                String[] splitstationname = strResult[0].split("_");
                if(splitstationname.length ==2) {
                    name_hosunname.put(splitstationname[1], strResult[0]);
                }
                else{
                    splitstationname[1] = splitstationname[1]+"_"+splitstationname[2];
                    name_hosunname.put(splitstationname[1], strResult[0]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();

            /*txtResult.setText("위치정보 : " + provider + "\n" +
                    "위도 : " + longitude + "\n" +
                    "경도 : " + latitude + "\n" +
                    "고도  : " + altitude);*/

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    public double calculator(double first_x, double first_y, double second_x, double second_y) {
        return ((first_x - second_x) * (first_x - second_x)) + ((first_y - second_y) * (first_y - second_y));
    }

    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {

            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public String[] settingList() {
        String namelist = loadJSONFromAsset("justNameList.json");

        try {
            JSONArray jarray = new JSONArray(namelist);   // JSONArray 생성
            String[] nameStringarray = new String[jarray.length()];
            for (int i = 0; i < jarray.length(); i++) {
                String jObject = jarray.getString(i);  // JSONObject 추출
                nameStringarray[i] = jObject;
            }
            //Arrays.sort(nameStringarray);
            return nameStringarray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void runFirstComplete() {
        AutoFirstTextView tvAutoFirst = (AutoFirstTextView) findViewById(R.id.autoComplete);
        ArrayList<String> rstList = new ArrayList<>();
        if (tvAutoFirst.getText().length() > 1) {
            String[] namestringarray = settingList();
            for (int i = 0; i < namestringarray.length; i++) {
                boolean bResult = SoundSearch.matchString(namestringarray[i], tvAutoFirst.getText().toString());
                if (bResult) {
                    rstList.add(namestringarray[i]);
                }
            }
            if (rstList.size() > 0) {

                String rstItem[] = new String[rstList.size()];
                for (int i = 0; i < rstList.size(); i++) {
                    rstItem[i] = rstList.get(i);
                }
                tvAutoFirst.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, rstItem));
                tvAutoFirst.showDropDown();
            } else {
                tvAutoFirst.dismissDropDown();
            }
        } else {
            tvAutoFirst.dismissDropDown();
        }
    }

    class backThread extends Thread{
        public void run(){
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "getInstanceId failed", task.getException());
                                return;
                            }

                            // Get new Instance ID token
                            String token = task.getResult().getToken();

                            // Log and toast
                            msg = getString(R.string.msg_token_fmt, token);
                            Log.d(TAG, msg);
                            String[] token_msg = msg.split(":");
                            token_msg[1]= token_msg[1].trim();
                            String tokenID = FirebaseInstanceId.getInstance().getToken();
                            if(databaseReference.getKey() != tokenID) {
                                databaseReference.child(tokenID).push().setValue(token_msg[1] + ":" + token_msg[2]);
                            }
                           // Toast.makeText(main_page.this, msg, Toast.LENGTH_SHORT).show();

                        }
                    });

        }
    }


}




