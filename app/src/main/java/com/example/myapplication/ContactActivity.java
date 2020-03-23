package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        text=findViewById(R.id.text);
        Intent intent= getIntent();
        String mobile=intent.getExtras().getString("mobile");
        text.setText("Contact +91-"+mobile+" for further information");
    }
}
