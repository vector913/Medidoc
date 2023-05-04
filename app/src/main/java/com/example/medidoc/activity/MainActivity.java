package com.example.medidoc.activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medidoc.R;
import com.example.medidoc.library.StandardLib;
import com.example.medidoc.library.StringLib;

//Note 시작 Activity
public class MainActivity extends AppCompatActivity {
    Button to_main;
    TextView join;
    TextView to_id;
    TextView to_pswd;
    CheckBox maintain;
    SharedPreferences settings;
    EditText id_input;
    EditText pswd_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        to_main =  findViewById(R.id.login_button);
        join = findViewById(R.id.join);
        to_id =  findViewById(R.id.find_id);
        to_pswd =  findViewById(R.id.find_password);
        maintain = findViewById(R.id.checkBox);
        settings = getApplicationContext().getSharedPreferences(getResources().getString(R.string.prefKeyName),0);
        id_input = findViewById(R.id.editText_id);
        pswd_input = findViewById(R.id.editText_password);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StandardLib.openActivity(MainActivity.this, JoinActivity.class);
            }
        });

        to_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StandardLib.openActivity(MainActivity.this, FindIdActivity.class);
            }
        });

        to_pswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StandardLib.openActivity(MainActivity.this, FindPswdActivity.class);
            }
        });

        to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = settings.getString("userid",null);
                String pswd = settings.getString("userpswd",null);
                String ids = id_input.getText().toString();
                String pswds = pswd_input.getText().toString();
                if(id!=null&&pswd!=null) {
                    if (id.equals(ids) && pswd.equals(pswds)) {

                        SharedPreferences.Editor editor = settings.edit();
                        if (maintain.isChecked()) {
                            editor.putInt("maintain", 1);
                        } else {
                            editor.putInt("maintain", 0);
                        }
                        editor.apply();
                        StandardLib.openActivity(MainActivity.this, MainViewActivity.class);
                    }else if (ids.equals("Username") || pswds.equals("****")||
                            StringLib.isEmpty(ids)|| StringLib.isEmpty(pswds)) {
                        Toast.makeText(getApplicationContext(), "아이디나 비밀번호가 입력되지 않았습니다!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "아이디나 비밀번호가 올바르지 않습니다", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "아이디나 이메일이 등록된것이 없습니다", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(1 == settings.getInt("maintain", 0)){
            StandardLib.openActivity(MainActivity.this, MainViewActivity.class);
        }
    }
}
