package com.example.medidoc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

public class join extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_layout);

        SharedPreferences settings = getApplicationContext().getSharedPreferences("mediSettings",0);
        String mediSettings = settings.getString("mediSettings",null);
        SharedPreferences.Editor editor = settings.edit();

        final EditText id = findViewById(R.id.join_id);
        final EditText pswd = findViewById(R.id.join_password);
        final EditText pswd_ch = findViewById(R.id.join_password_check);
        final EditText birthdate = findViewById(R.id.join_calender);
        final RadioButton rb = findViewById(R.id.join_boy);
        final RadioButton rg = findViewById(R.id.join_girl);
        final  EditText weight = findViewById(R.id.join_weight);
        final EditText spec = findViewById(R.id.join_specific);
        final   EditText email = findViewById(R.id.join_email);
        final   Button chdoub = findViewById(R.id.join_button_check);
        final    Button confirm = findViewById(R.id.join_button_confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ids = id.getText().toString();
                if(ids == null){
                    Toast.makeText(getApplicationContext(),"ID를 입력해주세요!", Toast.LENGTH_LONG);
                }
            }
        });

        }
}