package com.example.medidoc;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class main_view extends AppCompatActivity {

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;
    private GpsInfo gps;
    private Thread workerthread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_layout);

        Button for_gps = findViewById(R.id.GPS_info_button);
        Button for_set = findViewById(R.id.status_to_setting);
        final TextView gpsdata1 = findViewById(R.id.GPS_info1);
        final TextView gpsdata2 = findViewById(R.id.GPS_info2);

        final Handler handler = new Handler();
        final int delay = 10000; //milliseconds
        final SharedPreferences settings = getApplicationContext().getSharedPreferences("mediSettings",0);

        workerthread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()) {
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            String lat = settings.getString("latitute", null);
                            String lot = settings.getString("longtitude", null);

                            if (lat != null && lot != null) {
                                if (!isPermission) {
                                    callPermission();
                                    return;

                                }
                                gps = new GpsInfo(main_view.this);
                                // GPS 사용유무 가져오기
                                if (gps.isGetLocation()) {

                                    double latitude = gps.getLatitude();
                                    double longitude = gps.getLongitude();

                                    String sumd1 = "위도 : " + latitude;
                                    String sumd2 = "경도 : " + longitude;

                                    gpsdata1.setText(sumd1);
                                    gpsdata2.setText(sumd2);

                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putFloat("longtitude", Float.valueOf(String.valueOf(longitude)));
                                    editor.putFloat("latitude", Float.valueOf(String.valueOf(latitude)));
                                    editor.apply();
                                } else {
                                    // GPS 를 사용할수 없으므로
                                    gps.showSettingsAlert();
                                }
                            }
                            handler.postDelayed(this, delay);
                        }
                    }, delay);
                }

            }
        });
       // workerthread.start();
        for_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_find_id = new Intent(v.getContext(),setting.class);
                startActivityForResult(to_find_id,0);
            }
        });

        for_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPermission) {
                    callPermission();
                    return;
                }
                gps = new GpsInfo(main_view.this);
                // GPS 사용유무 가져오기
                if (gps.isGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    String sumd1 ="위도 : " +latitude;
                    String sumd2 ="경도 : " +longitude;

                    gpsdata1.setText(sumd1);
                    gpsdata2.setText(sumd2);

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("longtitude",String.valueOf(longitude));
                    editor.putString("latitude",String.valueOf(latitude));
                    editor.apply();
                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }

            }

        });
    }
    @Override

    public void onRequestPermissionsResult(int requestCode, String[] permissions,

                                           int[] grantResults) {

        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION

                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {



            isAccessFineLocation = true;



        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION

                && grantResults[0] == PackageManager.PERMISSION_GRANTED){



            isAccessCoarseLocation = true;

        }



        if (isAccessFineLocation && isAccessCoarseLocation) {

            isPermission = true;

        }

    }



// 전화번호 권한 요청

    private void callPermission() {

        // Check the SDK version and whether the permission is already granted or not.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)

                != PackageManager.PERMISSION_GRANTED) {



            requestPermissions(

                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},

                    PERMISSIONS_ACCESS_FINE_LOCATION);



        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)

                != PackageManager.PERMISSION_GRANTED){



            requestPermissions(

                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},

                    PERMISSIONS_ACCESS_COARSE_LOCATION);

        } else {

            isPermission = true;

        }

    }



}
