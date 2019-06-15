package com.example.medidoc;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class join extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_layout);

        final SharedPreferences settings = getApplicationContext().getSharedPreferences("mediSettings",0);
        final Calendar myCalendar = Calendar.getInstance();
        final RadioGroup rgroup = findViewById(R.id.join_rg);
        final EditText id = findViewById(R.id.join_id);
        final EditText pswd = findViewById(R.id.join_password);
        final EditText pswd_ch = findViewById(R.id.join_password_check);
        final EditText birthdate = findViewById(R.id.join_calender);
        final EditText weight = findViewById(R.id.join_weight);
        final EditText spec = findViewById(R.id.join_specific);
        final EditText email = findViewById(R.id.join_email);
        final Button chdoub = findViewById(R.id.join_button_check);
        final Button confirm = findViewById(R.id.join_button_confirm);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                birthdate.setText(sdf.format(myCalendar.getTime()));
            }

        };
       chdoub.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"준비중입니다.", Toast.LENGTH_LONG).show();
            }
        });

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(join.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int check = 0;
                String ids = id.getText().toString();
              if(ids.equals("아이디")&&check!=1){
                    Toast.makeText(getApplicationContext(),"ID를 입력해주세요!", Toast.LENGTH_LONG).show();
                    check = 1;
              }

                String pswds = pswd.getText().toString();
                if(pswds.equals("비밀번호")&&check!=1){
                    Toast.makeText(getApplicationContext(),"패스워드를 입력해주세요!", Toast.LENGTH_LONG).show();
                    check = 1;
                }
                String pswchs = pswd_ch.getText().toString();
                if(!pswchs.equals(pswds)&&check!=1){
                    Toast.makeText(getApplicationContext(),"비밀번호 확인이 같지 않습니다.", Toast.LENGTH_LONG).show();
                    check = 1;
                }

                String birthdates = birthdate.getText().toString();
                if( birthdates.equals("생년월일")&&check!=1){
                    Toast.makeText(getApplicationContext(),"생년 월일을 입력해주세요!", Toast.LENGTH_LONG).show();
                    check = 1;
                }
               int sexs = rgroup.getCheckedRadioButtonId();
                if(sexs == R.id.join_boy){
                    sexs = 0;
                }else if(sexs == R.id.join_girl){
                    sexs = 1;
                }else if(check!=1){
                    Toast.makeText(getApplicationContext(),"성별을 선택바랍니다.",Toast.LENGTH_LONG).show();
                    check = 1;
                }
                int weights = 0;
                String tmp = weight.getText().toString();
                if(tmp.equals("몸무게")&&check!=1){
                    Toast.makeText(getApplicationContext(),"몸무게를 입력해주세요!", Toast.LENGTH_LONG).show();
                    check = 1;
                }else if (check == 0){
                    weights = parseInt(tmp);
                }

                String specifis = spec.getText().toString();
                if(specifis.equals("특이사항")&&check!=1){
                    Toast.makeText(getApplicationContext(),"특이사항을 입력해주세요!", Toast.LENGTH_LONG).show();
                    check = 1;
                }
                 String emails = email.getText().toString();
                if(emails.equals("examle@email.com")&&check!=1){
                    Toast.makeText(getApplicationContext(),"email을 입력해주세요!", Toast.LENGTH_LONG).show();
                    check = 1;
                }

                if(check == 0) {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("userid",ids);
                    editor.putString("userpswd",pswds);
                    editor.putInt("userweight",weights);
                    editor.putString("userbirth",birthdates);
                    editor.putString("userspec",specifis);
                    editor.putString("useremail",emails);
                    editor.putInt("usersex",sexs);
                    editor.apply();
                    Intent to_find_id = new Intent(v.getContext(),MainActivity.class);
                    startActivityForResult(to_find_id, 0);
                }
            }
        });

        }
}