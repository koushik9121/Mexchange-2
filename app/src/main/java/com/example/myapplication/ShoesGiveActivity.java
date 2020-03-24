package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ShoesGiveActivity extends AppCompatActivity {

    EditText mobile;
    EditText product_desc;
    EditText name;
    EditText userid;
    Button add;
    DatabaseReference ref;
    FirebaseAuth auth;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoes_give);

        name=findViewById(R.id.editText);
        userid=findViewById(R.id.editText2);
        pd=new ProgressDialog(this);
        mobile=findViewById(R.id.mobile_get);
        product_desc=findViewById(R.id.product_description);
        add=findViewById(R.id.add_product);
        ref= FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile_txt=mobile.getText().toString();
                String product_txt=product_desc.getText().toString();
                String name_txt=name.getText().toString();
                String userid_txt=userid.getText().toString();
                addProduct(name_txt,userid_txt,mobile_txt,product_txt);
            }
        });

    }

    private void addProduct(String name,String userid,String mobile,String product){
        pd.setMessage("Adding Product");
        pd.show();
        HashMap<String,Object> map= new HashMap<>();
        map.put("name",name);
        map.put("userid",userid);
        map.put("mobile",mobile);
        map.put("product description",product);

        ref.child("game_controller").child(auth.getCurrentUser().getUid()).push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    pd.dismiss();
                    Toast.makeText(ShoesGiveActivity.this,"Added Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ShoesGiveActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
