package com.example.medidoc.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static java.lang.Integer.parseInt;

import com.example.medidoc.R;
import com.example.medidoc.library.StringLib;

public class joinactivity extends AppCompatActivity {

    //Note 회원가입 절차에 필요한 View들.
    SharedPreferences settings;
    Calendar myCalendar;
    RadioGroup rgroup;
    EditText id;
    EditText pswd;
    EditText pswd_ch;
    EditText birthdate;
    EditText weight;
    EditText spec;
    EditText email;
    Button chdoub;
    Button confirm;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_layout);

        settings = getApplicationContext().getSharedPreferences(getResources().getString(R.string.prefKeyName),0);
        myCalendar = Calendar.getInstance();
        rgroup = findViewById(R.id.join_rg);
        RadioButton radioButton = findViewById(R.id.join_boy);
        //Note Default Select
        radioButton.setSelected(true);
        id = findViewById(R.id.join_id);
        pswd = findViewById(R.id.join_password);
        pswd_ch = findViewById(R.id.join_password_check);
        birthdate = findViewById(R.id.join_calender);
        weight = findViewById(R.id.join_weight);
        spec = findViewById(R.id.join_specific);
        email = findViewById(R.id.join_email);
        chdoub = findViewById(R.id.join_button_check);
        confirm = findViewById(R.id.join_button_confirm);

        setEventListener();
    }

    public void setEventListener(){
         date = new DatePickerDialog.OnDateSetListener() {
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
                new DatePickerDialog(joinactivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ids = id.getText().toString();
                if(ids.equals("아이디")||
                        StringLib.isEmpty(ids)){
                    Toast.makeText(getApplicationContext(),"ID를 입력해주세요!", Toast.LENGTH_LONG).show();
                    return;
                }

                String pswds = pswd.getText().toString();
                if(pswds.equals("비밀번호")||
                        StringLib.isEmpty(pswds)){
                    Toast.makeText(getApplicationContext(),"패스워드를 입력해주세요!", Toast.LENGTH_LONG).show();
                    return;
                }
                String pswchs = pswd_ch.getText().toString();
                if(!pswchs.equals(pswds)){
                    Toast.makeText(getApplicationContext(),"비밀번호 확인이 같지 않습니다.", Toast.LENGTH_LONG).show();
                    return;
                }

                String birthdates = birthdate.getText().toString();
                if( birthdates.equals("생년월일")||
                        StringLib.isEmpty(birthdates)){
                    Toast.makeText(getApplicationContext(),"생년 월일을 입력해주세요!", Toast.LENGTH_LONG).show();
                    return;
                }
                int sexs = rgroup.getCheckedRadioButtonId();
                if(sexs == R.id.join_boy){
                    sexs = 0;
                }else if(sexs == R.id.join_girl){
                    sexs = 1;
                }

                int weights;
                String tmp = weight.getText().toString();
                if(tmp.equals("몸무게")||
                        StringLib.isEmpty(tmp)){
                    Toast.makeText(getApplicationContext(),"몸무게를 입력해주세요!", Toast.LENGTH_LONG).show();
                    return;
                }
                try
                {
                    weights = parseInt(tmp);
                }catch (ParseException e)
                {
                    Toast.makeText(getApplicationContext(),"몸무게에 정상적인 숫자를 입력해주세요!", Toast.LENGTH_LONG).show();
                    return;
                }

                String specifis = spec.getText().toString();

                if(specifis.equals("특이사항")||
                        StringLib.isEmpty(specifis)){
                    Toast.makeText(getApplicationContext(),"특이사항을 입력해주세요!", Toast.LENGTH_LONG).show();
                    return;
                }
                String emails = email.getText().toString();
                if(emails.equals("examle@email.com")||
                        StringLib.isEmpty(emails)){
                    Toast.makeText(getApplicationContext(),"email을 입력해주세요!", Toast.LENGTH_LONG).show();
                    return;
                }

                SharedPreferences.Editor editor = settings.edit();
                editor.putString("userid",ids);
                editor.putString("userpswd",pswds);
                editor.putInt("userweight",weights);
                editor.putString("userbirth",birthdates);
                editor.putString("userspec",specifis);
                editor.putString("useremail",emails);
                editor.putInt("usersex",sexs);
                editor.apply();

                setResult(RESULT_OK);
                finish();
            }
        });
    }
}