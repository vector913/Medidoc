package com.example.medidoc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class find_id extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.id_find_layout);

        Button send = findViewById(R.id.find_id_button);
        EditText email = findViewById(R.id.find_id_email);

        String recipientList = email.getText().toString();
        final String[] recipients = recipientList.split(",");
        final SharedPreferences settings = getApplicationContext().getSharedPreferences("mediSettings", 0);
        final String emails = email.getText().toString();
        String ids = settings.getString("userid", null);
        final String subject = emails;
        final String message = "Your id is : " + ids;

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!emails.equals("이메일 입력")) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    intent.putExtra(Intent.EXTRA_TEXT, message);
                    intent.setType("message/rfc822");
                    startActivity(Intent.createChooser(intent, "Choose an email client"));
                }else{
                    Toast.makeText(getApplicationContext(),"이메일이 올바르지 않습니다", Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}