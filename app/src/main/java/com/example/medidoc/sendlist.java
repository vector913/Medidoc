package com.example.medidoc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class sendlist extends AppCompatActivity {

    ArrayList<phonebook> phonebooklist;
    CustomAdapter adapter;
    Button sender_add ;
    Button closes;
    Button test;
    Set<String> phonel;
    Set<String> namel;
    ArrayList<String> names;
    ArrayList<String> phones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sender_layout);
        final ListView lv;
        sender_add = findViewById(R.id.sender_add);
        closes = findViewById(R.id.sender_close);
        final SharedPreferences number_list = getApplicationContext().getSharedPreferences("mediSettings", 0);

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
                    try{
                        SmsManager smgr = SmsManager.getDefault();
                        phones = new ArrayList<String>(phonel);
                        names = new ArrayList<String>(namel);
                        String id = number_list.getString("userid",null);
                        for(int i = 0;i<phones.size();i++) {
                            smgr.sendTextMessage(phones.get(i), null, names.get(i)+"님! \n이 문자 메세지는 "+id+"의 응급 사고시 발송되는 예시 문자입니다. 확인후 응답 바랍니다.", null, null);
                        }
                        Toast.makeText(getApplicationContext(), "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                    }

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
                                phonel = new HashSet<String>();
                                namel = new HashSet<String>();
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
              Intent to_find_id = new Intent(v.getContext(),main_view.class);
              startActivityForResult(to_find_id,0);
          }
      });

      sender_add.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
               Intent to_find_id = new Intent(v.getContext(),sender_add.class);
                startActivityForResult(to_find_id,0);
           }
       });
    }

}
