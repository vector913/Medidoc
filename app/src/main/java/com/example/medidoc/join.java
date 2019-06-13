package com.example.medidoc;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class join extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_layout);

        final SharedPreferences settings = getApplicationContext().getSharedPreferences("mediSettings",0);
        final String mediSettings = settings.getString("mediSettings",null);
        final Calendar myCalendar = Calendar.getInstance();
        final RadioGroup rgroup = findViewById(R.id.join_rg);
        final EditText id = findViewById(R.id.join_id);
        final EditText pswd = findViewById(R.id.join_password);
        final EditText pswd_ch = findViewById(R.id.join_password_check);
        final EditText birthdate = findViewById(R.id.join_calender);
        final RadioButton rb = findViewById(R.id.join_boy);
        final RadioButton rg = findViewById(R.id.join_girl);
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

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int check = 0;
                String ids = id.getText().toString();
                if(ids == null){
                    Toast.makeText(getApplicationContext(),"ID를 입력해주세요!", Toast.LENGTH_LONG);
                    check = 1;
                }
                String pswds = pswd.getText().toString();
                if(pswds == null){
                    Toast.makeText(getApplicationContext(),"패스워드를 입력해주세요!", Toast.LENGTH_LONG);
                    check = 1;
                }
                String pswchs = pswd_ch.getText().toString();
                if(pswchs == null){
                    Toast.makeText(getApplicationContext(),"ID를 입력해주세요!", Toast.LENGTH_LONG);
                    check = 1;
                }
                birthdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        new DatePickerDialog(join.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
                String birthdates = birthdate.getText().toString();
                if( birthdates == null){
                    Toast.makeText(getApplicationContext(),"생년 월일을 입력해주세요!", Toast.LENGTH_LONG);
                    check = 1;
                }
                int sexs = rgroup.getCheckedRadioButtonId();
                if(sexs == R.id.join_boy){
                    sexs = 0;
                }else if(sexs == R.id.join_girl){
                    sexs = 1;
                }else{
                    Toast.makeText(getApplicationContext(),"성별을 선택바랍니다.",Toast.LENGTH_LONG);
                    check = 1;
                }
                int weights;
                String tmp = weight.getText().toString();
                weights = Integer.parseInt(tmp);
                if(weights == 0){
                    Toast.makeText(getApplicationContext(),"몸무게를 입력해주세요!", Toast.LENGTH_LONG);
                    check = 1;
                }
                String specifis = spec.getText().toString();
                if(specifis == null){
                    Toast.makeText(getApplicationContext(),"특이사항을 입력해주세요!", Toast.LENGTH_LONG);
                    check = 1;
                }
                String emails = email.getText().toString();
                if(emails == null){
                    Toast.makeText(getApplicationContext(),"email을 입력해주세요!", Toast.LENGTH_LONG);
                    check = 1;
                }
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("userid",ids);
                editor.putString("userpswd",pswds);
                editor.putInt("userweight",weights);
                editor.putString("userspec",specifis);
                editor.putString("useremail",emails);
                editor.putInt("usersex",sexs);
                Intent to_find_id = new Intent(v.getContext(),join.class);
                startActivityForResult(to_find_id,0);
            }
        });

        }
}