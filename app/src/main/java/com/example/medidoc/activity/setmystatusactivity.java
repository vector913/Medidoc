package com.example.medidoc.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.medidoc.R;


public class setmystatusactivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfo_layout);

        TextView id = findViewById(R.id.my_info_id);
        final EditText pswd = findViewById(R.id.my_info_password);
        final EditText pswd_ch = findViewById(R.id.my_info_password_check);
        TextView calender = findViewById(R.id.my_info_calender);
        TextView sex = findViewById(R.id.my_info_sex);
        TextView weight = findViewById(R.id.my_info_weight);
        TextView specific = findViewById(R.id.my_info_specific);
        TextView email = findViewById(R.id.my_info_email);
        Button confirm = findViewById(R.id.my_info_button_confirm);

        final SharedPreferences settings = getApplicationContext().getSharedPreferences(getResources().getString(R.string.prefKeyName),0);

        id.setText("아이디 : "+settings.getString("userid",null));
        calender.setText(settings.getString("userbirth",null));
        int sexnum = settings.getInt("usersex",0);
        if(sexnum == 0){
            sex.setText("성별 : 남자");
        }else if(sexnum==1){
            sex.setText("성별 : 여자");
        }
       weight.setText("몸무게 : "+settings.getInt(""+"userweight",0));
        specific.setText("특이사항 : "+settings.getString("userspec",null));
        email.setText("email : "+settings.getString("useremail",null));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pswds = pswd.getText().toString();
                String pswd_chs =pswd_ch.getText().toString();
                if(pswds.equals(pswd_chs)){
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("userpswd",pswds);
                    editor.apply();
                    Intent to_main_view = new Intent(v.getContext(), mainViewactivity.class);
                    startActivityForResult(to_main_view, 0);
                }else{
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치 하지 않습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
