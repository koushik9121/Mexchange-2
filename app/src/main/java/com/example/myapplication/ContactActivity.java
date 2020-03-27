package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {

    TextView mobile;
    TextView name;
    TextView userid;
    Button request;
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
        name.setText(name_txt);
        userid.setText(userid_txt);
        mobile.setText("+91-"+mobile_txt);
        request = (Button)findViewById(R.id.request);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContactActivity.this,"Request Sent!",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ContactActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
