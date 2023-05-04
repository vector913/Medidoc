package com.example.medidoc.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.medidoc.R;
import com.example.medidoc.data.adapter.CustomAdapter;
import com.example.medidoc.data.phonebook;

import java.util.ArrayList;
import java.util.Set;

public class SendListActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS =1;
    ArrayList<phonebook> phonebooklist;
    CustomAdapter adapter;
    Button sender_add ;
    Button closes;
    Button test;
    Set<String> phonel;
    Set<String> namel;
    ArrayList<String> names;
    ArrayList<String> phones;
    SharedPreferences number_list ;
    private static final int PERMISSION_SEND_SMS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sender_layout);
        final ListView lv;
        sender_add = findViewById(R.id.sender_add);
        closes = findViewById(R.id.sender_close);

        number_list= getApplicationContext().getSharedPreferences(getResources().getString(R.string.prefKeyName), 0);

        phonel = number_list.getStringSet("phonenum", null);
        namel = number_list.getStringSet("phonename", null);
        test = findViewById(R.id.buttontest);

        if (phonel != null && namel != null) {
            names = new ArrayList<String>(namel);
            phones = new ArrayList<String>(phonel);
            phonebooklist = new ArrayList<phonebook>();
            for (int i = 0; i < names.size(); i++) {
                phonebooklist.add(new phonebook(names.get(i), phones.get(i)));
            }
            lv = findViewById(R.id.listView);
            adapter = new CustomAdapter(this, phonebooklist);

            lv.setAdapter(adapter);

            test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestSmsPermission();
                }
            });

        lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("정말로 연락처를 삭제하시겠습니까?");
                builder1.setCancelable(true);

                builder1.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                                phonebooklist.remove(position);
                                names.remove(position);
                                phones.remove(position);
                                //lv.clearChoices();
                                adapter.notifyDataSetChanged();
                                SharedPreferences.Editor editor = number_list.edit();
                                phonel.clear();
                                namel.clear();
                                namel.addAll(names);
                                phonel.addAll(phones);
                                editor.putStringSet("phonenum", phonel);
                                editor.putStringSet("phonename", namel);
                                editor.apply();
                                dialog.cancel();
                    }
                });
                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert1 = builder1.create();
                alert1.show();
            }
        });
    }
        closes.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent to_find_id = new Intent(v.getContext(), MainViewActivity.class);
              startActivityForResult(to_find_id,0);
          }
      });

      sender_add.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
               Intent to_find_id = new Intent(v.getContext(), SenderAddActivity.class);
                startActivityForResult(to_find_id,0);
           }
       });
    }
    private void requestSmsPermission() {

        // check permission is given
        if (ContextCompat.checkSelfPermission(SendListActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            // request permission (see result in onRequestPermissionsResult() method)
            ActivityCompat.requestPermissions(SendListActivity.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_SEND_SMS);
        } else {
            // permission already granted run sms send
            sendSms();
        }
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSms();
                    Toast.makeText(getApplicationContext(), "SMS 전송에 성공하였습니다.", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "SMS 전송에 실패했습니다. 권한 휙득 실패", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
    private void sendSms(){
        SmsManager smgr = SmsManager.getDefault();
        phones = new ArrayList<String>(phonel);
        names = new ArrayList<String>(namel);
        String id = number_list.getString("userid",null);
        for(int i = 0;i<phones.size();i++) {
            smgr.sendTextMessage(phones.get(i), null, names.get(i)+"님! \n이 문자 메세지는 "+id+"의 응급 사고시 발송되는 예시 문자입니다. 확인후 응답 바랍니다.", null, null);
        }
    }
}
