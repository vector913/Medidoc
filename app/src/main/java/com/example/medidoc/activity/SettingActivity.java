package com.example.medidoc.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.medidoc.R;
import com.example.medidoc.library.StandardLib;

public class SettingActivity extends AppCompatActivity {
    //설정 Activity의 Button들
    Button exit;
    Button senderlist;
    Button deviceconnect;
    Button infosetting;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        exit = findViewById(R.id.exit_setting);
        senderlist = findViewById(R.id.to_send_text_setting);
        deviceconnect = findViewById(R.id.device_setting);
        infosetting = findViewById(R.id.my_info_setting);
        reset = findViewById(R.id.info_reset);

        setEventlistener();
    }

    public void setEventlistener()
    {
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setResult(RESULT_OK);
               finish();
            }
        });

        senderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StandardLib.openActivity(SettingActivity.this,SendListActivity.class);
            }
        });
        deviceconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StandardLib.openActivity(SettingActivity.this,BluetoothActivity.class);
            }
        });
        infosetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StandardLib.openActivity(SettingActivity.this,SetMyStatusActivity.class);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingActivity.this);
                alertDialogBuilder
                        .setTitle("정보 초기화")
                        .setMessage("정보를 정말로 초기화하겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final SharedPreferences settings = getApplicationContext().getSharedPreferences(getResources().getString(R.string.prefKeyName),0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.clear();
                                editor.apply();
                                StandardLib.openActivity(SettingActivity.this, JoinActivity.class);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }
        });
    }
}