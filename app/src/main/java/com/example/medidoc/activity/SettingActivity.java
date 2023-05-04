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
                Intent to_sender_list = new Intent(v.getContext(), SendListActivity.class);
                startActivityForResult(to_sender_list,0);
            }
        });
        deviceconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_bluetooth = new Intent(v.getContext(), BluetoothActivity.class);
                startActivityForResult(to_bluetooth,0);
            }
        });
        infosetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_sender_list = new Intent(v.getContext(), SetMyStatusActivity.class);
                startActivityForResult(to_sender_list,0);
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
                                Intent to_main = new Intent(v.getContext(), JoinActivity.class);
                                startActivityForResult(to_main,0);
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