package com.example.medidoc.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.medidoc.R;

public class settingactivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        final  int REQUEST_ENABLE_BT = 1;
        final Context context = this;
        Button exit = findViewById(R.id.exit_setting);
        Button senderlist = findViewById(R.id.to_send_text_setting);
        Button deviceconnect = findViewById(R.id.device_setting);
        Button infosetting = findViewById(R.id.my_info_setting);
        Button reset = findViewById(R.id.info_reset);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_main = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(to_main,0);
            }
        });

        senderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_sender_list = new Intent(v.getContext(), sendlistactivity.class);
                startActivityForResult(to_sender_list,0);
            }
        });
        deviceconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                Intent to_bluetooth = new Intent(v.getContext(), bluetoothactivity.class);
                startActivityForResult(to_bluetooth,0);
            }
        });
        infosetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_sender_list = new Intent(v.getContext(), setmystatusactivity.class);
                startActivityForResult(to_sender_list,0);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("정보 초기화");

                alertDialogBuilder
                        .setMessage("정보를 정말로 초기화하겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final SharedPreferences settings = getApplicationContext().getSharedPreferences("mediSettings",0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.clear();
                                editor.apply();
                                Intent to_main = new Intent(v.getContext(), joinactivity.class);
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