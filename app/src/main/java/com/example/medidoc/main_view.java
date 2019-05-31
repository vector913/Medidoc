package com.example.medidoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class main_view extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_layout);

        Button for_set = findViewById(R.id.status_to_setting);

        for_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_find_id = new Intent(v.getContext(),setting.class);
                startActivityForResult(to_find_id,0);
            }
        });
    }


}
