package com.example.medidoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button to_main =  findViewById(R.id.login_button);
       // CheckBox checked = (CheckBox) findViewById(R.id.checkBox);

        TextView join = findViewById(R.id.join);
        TextView to_id =  findViewById(R.id.find_id);
        TextView to_pswd =  findViewById(R.id.find_password);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_find_id = new Intent(v.getContext(),join.class);
                startActivityForResult(to_find_id,0);
            }
        });

        to_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_find_id = new Intent(v.getContext(),find_id.class);
                startActivityForResult(to_find_id,0);
            }
        });

       to_pswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_find_id = new Intent(v.getContext(),find_pswd.class);
                startActivityForResult(to_find_id,0);
            }
        });

        to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_main_view = new Intent(v.getContext(),main_view.class);
                startActivityForResult(to_main_view,0);
            }
        });
    }
}
