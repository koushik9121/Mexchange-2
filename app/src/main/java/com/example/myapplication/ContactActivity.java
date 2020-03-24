package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

    TextView mobile;
    TextView name;
    TextView userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        name=findViewById(R.id.name);
        userid=findViewById(R.id.userid);
        mobile=findViewById(R.id.mobile);
        Intent intent= getIntent();
        String mobile_txt=intent.getExtras().getString("mobile");
        String name_txt=intent.getExtras().getString("name");
        String userid_txt=intent.getExtras().getString("userid");
        name.setText("Offered by "+name_txt);
        userid.setText("Roll No: "+userid_txt);
        mobile.setText("Contact +91-"+mobile_txt+" for further information");
    }
}
