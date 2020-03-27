package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class BadGetActivity extends AppCompatActivity {

    ListView listView;
    DatabaseReference ref;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_get);

        pd=new ProgressDialog(this);
        listView=findViewById(R.id.listView);
        final ArrayList<String> list=new ArrayList<>();
        final ArrayList<String> list_outer=new ArrayList<>();
        final ArrayList<String> list_inner=new ArrayList<>();
        final ArrayList<String> mobile_list=new ArrayList<>();
        final ArrayList<String> names=new ArrayList<>();
        final ArrayList<String> userids=new ArrayList<>();
        final ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.list_item,list);
        listView.setAdapter(adapter);

        ref= FirebaseDatabase.getInstance().getReference().child("badminton");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                list_outer.clear();
                list_inner.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    for (DataSnapshot k : snapshot.getChildren()) {
                        String s=snapshot.getKey();
                        String key=k.getKey();
                        String mob=k.child("mobile").getValue().toString();
                        String name=k.child("name").getValue().toString();
                        String userid=k.child("userid").getValue().toString();
                        list_outer.add(s);
                        list_inner.add(key);
                        mobile_list.add(mob);
                        names.add(name);
                        userids.add(userid);
                        final String product_desc = k.child("product description").getValue().toString();
                        list.add(product_desc);
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
                String requested_from=list_outer.get(position);
                String requested_item=list_inner.get(position);
                String mobile=mobile_list.get(position);
                String name=names.get(position);
                String userid=userids.get(position);
                //ref.child(s).child(key).removeValue();
                //adapter.remove(item);
                //adapter.notifyDataSetChanged();
                //Toast.makeText(BadGetActivity.this,"Product successfully Requested",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(BadGetActivity.this,ContactActivity.class);
                intent.putExtra("mobile",mobile);
                intent.putExtra("name",name);
                intent.putExtra("userid",userid);
                intent.putExtra("requested_from",requested_from);
                intent.putExtra("requested_item",requested_item);
                intent.putExtra("item_desc",item);
                startActivity(intent);
            }
        });

    }
}
