package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RequestsActivity extends AppCompatActivity {

    ListView listView;
    DatabaseReference ref;
    DatabaseReference ref2;
    DatabaseReference ref3;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        listView=findViewById(R.id.list_requests);
        final ArrayList<String> list=new ArrayList<>();
        final ArrayList<String> items=new ArrayList<>();
        final ArrayList<String> requests=new ArrayList<>();
        final ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.list_item,list);
        listView.setAdapter(adapter);
        auth=FirebaseAuth.getInstance();
        final String user=FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref=FirebaseDatabase.getInstance().getReference().child("requests").child(user);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                items.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    for(DataSnapshot k:snapshot.getChildren()){
                        String item=snapshot.getKey();
                        items.add(item);
                        String request=k.getKey();
                        requests.add(request);
                        String item_desc=k.child("item_desc").getValue().toString();
                        String requested_by=k.child("requested_by_roll").getValue().toString();
                        String disp=item_desc+" requested by "+requested_by;
                        list.add(disp);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=adapter.getItem(position).toString();
                String item_req=items.get(position);
                String request=requests.get(position);
                ref2=FirebaseDatabase.getInstance().getReference();
                ref2.child("requests").child(user).child(item_req).removeValue();
                ref3=FirebaseDatabase.getInstance().getReference();
                if(ref3.child("badminton").child(auth.getCurrentUser().getUid()).child(item_req)!=null){
                    ref3.child("badminton").child(auth.getCurrentUser().getUid()).child(item_req).removeValue();
                }
                else{
                    ref3.child("game_controller").child(auth.getCurrentUser().getUid()).child(item_req).removeValue();
                }
                Toast.makeText(RequestsActivity.this,"Request Approved",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RequestsActivity.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }
}
