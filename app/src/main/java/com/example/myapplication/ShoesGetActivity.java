package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShoesGetActivity extends AppCompatActivity {

    ListView listView;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_get);

        listView=findViewById(R.id.listView);
        final ArrayList<String> list=new ArrayList<>();
        final ArrayList<String> list_inner=new ArrayList<>();
        final ArrayList<String> list_outer=new ArrayList<>();
        final ArrayList<String> mobile=new ArrayList<>();
        final ArrayList<String> names=new ArrayList<>();
        final ArrayList<String> userids=new ArrayList<>();
        final ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.list_item,list);
        listView.setAdapter(adapter);

        ref= FirebaseDatabase.getInstance().getReference().child("game_controller");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    for (DataSnapshot k : snapshot.getChildren()) {
                        String s=snapshot.getKey();
                        String key=k.getKey();
                        String mob=k.child("mobile").getValue().toString();
                        String name=k.child("name").getValue().toString();
                        String userid=k.child("userid").getValue().toString();
                        final String productdesc = k.child("product description").getValue().toString();
                        list.add(productdesc);
                        list_inner.add(key);
                        mobile.add(mob);
                        list_outer.add(s);
                        names.add(name);
                        userids.add(userid);
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
                String s=list_outer.get(position);
                String key=list_inner.get(position);
                String mobile_no=mobile.get(position);
                String name=names.get(position);
                String userid=userids.get(position);
                //ref.child(s).child(key).removeValue();
                //Toast.makeText(ShoesGetActivity.this,"Product successfully Received",Toast.LENGTH_SHORT).show();
                //adapter.remove(item);
                //adapter.notifyDataSetChanged();
                Intent intent=new Intent(ShoesGetActivity.this,ContactActivity.class);
                intent.putExtra("mobile",mobile_no);
                intent.putExtra("name",name);
                intent.putExtra("userid",userid);
                intent.putExtra("requested_from",s);
                intent.putExtra("requested_item",key);
                intent.putExtra("item_desc",item);
                startActivity(intent);

            }
        });
    }
}
