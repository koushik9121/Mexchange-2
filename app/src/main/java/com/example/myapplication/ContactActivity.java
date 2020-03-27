package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ContactActivity extends AppCompatActivity {

    TextView mobile;
    TextView name;
    TextView userid;
    Button request;
    DatabaseReference ref;
    DatabaseReference ref2;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ref= FirebaseDatabase.getInstance().getReference();
        ref2=FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        name=findViewById(R.id.name);
        userid=findViewById(R.id.userid);
        mobile=findViewById(R.id.mobile);
        final Intent intent= getIntent();
        String mobile_txt=intent.getExtras().getString("mobile");
        final String name_txt=intent.getExtras().getString("name");
        final String userid_txt=intent.getExtras().getString("userid");
        name.setText(name_txt);
        userid.setText(userid_txt);
        mobile.setText("+91-"+mobile_txt);
        request = (Button)findViewById(R.id.request);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String requested_from=intent.getExtras().getString("requested_from");
                final String requested_item=intent.getExtras().getString("requested_item");
                final String item_desc=intent.getExtras().getString("item_desc");
                final String user=auth.getCurrentUser().getUid();
                ref2.child("Users").child(user).child("roll no").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        HashMap<String,Object> map2=new HashMap<>();
                        String requested_by_roll =dataSnapshot.getValue(String.class);
                        map2.put("requested_by_roll", requested_by_roll);
                        map2.put("item_desc",item_desc);
                        ref.child("requests").child(requested_from).child(requested_item).push().setValue(map2);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Toast.makeText(ContactActivity.this,"Request Sent!",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ContactActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
