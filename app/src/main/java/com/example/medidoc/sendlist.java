package com.example.medidoc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

public class sendlist extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sender_layout);

        Button sender_add = findViewById(R.id.sender_add);
        Button closes = findViewById(R.id.sender_close);
        final SharedPreferences number_list = getApplicationContext().getSharedPreferences("mediSettings",0);

        Set<String> phonel = number_list.getStringSet("phonenum",null);
        Set<String> namel = number_list.getStringSet("phonename",null);

        if(phonel!=null&&namel!=null) {
            ArrayList<String> names = new ArrayList<String>(namel);
            ArrayList<String> phones = new ArrayList<String>(phonel);

            ListView lv = findViewById(R.id.listView);

           final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
           adapter.addAll(phones);

           lv.setAdapter(adapter);

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
