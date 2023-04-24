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

//Note 시작 Activity
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button to_main =  findViewById(R.id.login_button);
        TextView join = findViewById(R.id.join);
        TextView to_id =  findViewById(R.id.find_id);
        TextView to_pswd =  findViewById(R.id.find_password);
        final CheckBox maintain = findViewById(R.id.checkBox);
        final SharedPreferences settings = getApplicationContext().getSharedPreferences("mediSettings",0);
        final EditText id_input = findViewById(R.id.editText_id);
        final EditText pswd_input = findViewById(R.id.editText_password);
        final int check = 1;

        if(check == settings.getInt("maintain", 0)){
            Intent to_main_view = new Intent(getApplicationContext(), mainViewactivity.class);
            startActivityForResult(to_main_view, 0);
        }

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_find_id = new Intent(v.getContext(), joinactivity.class);
                startActivityForResult(to_find_id,0);
            }
        });

        to_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_find_id = new Intent(v.getContext(), findidactivity.class);
                startActivityForResult(to_find_id,0);
            }
        });

       to_pswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_find_id = new Intent(v.getContext(), findpswdactivity.class);
                startActivityForResult(to_find_id,0);
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

                        Intent to_main_view = new Intent(v.getContext(), mainViewactivity.class);
                        startActivityForResult(to_main_view, 0);
                    }
                    else if (ids.equals("Username") || pswds.equals("****"))
                    {
                        Toast.makeText(getApplicationContext(), "아이디나 비밀번호가 입력되지 않았습니다!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "아이디나 비밀번호가 올바르지 않습니다", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "아이디나 이메일이 등록된것이 없습니다", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
