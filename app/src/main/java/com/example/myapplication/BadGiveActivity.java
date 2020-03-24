package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class BadGiveActivity extends AppCompatActivity {

    Button add_product;
    EditText name;
    EditText userid;
    EditText product_description;
    EditText mobile;
    DatabaseReference ref;
    FirebaseAuth auth;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_give);

        name=findViewById(R.id.editText);
        userid=findViewById(R.id.editText2);
        pd=new ProgressDialog(this);
        add_product=findViewById(R.id.add_product);
        product_description=findViewById(R.id.product_description);
        mobile=findViewById(R.id.mobile_get);
        auth=FirebaseAuth.getInstance();
        ref=FirebaseDatabase.getInstance().getReference();
        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile_txt=mobile.getText().toString();
                String product_txt=product_description.getText().toString();
                String name_txt=name.getText().toString();
                String userid_txt=userid.getText().toString();
                addProduct(name_txt,userid_txt,mobile_txt,product_txt);
            }
        });
    }

    private void addProduct(String name,String userid,String mobile,String product){
        pd.setMessage("Adding Product");
        pd.show();
        HashMap<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("userid",userid);
        map.put("mobile",mobile);
        map.put("product description",product);
        ref.child("badminton").child(auth.getCurrentUser().getUid()).push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    pd.dismiss();
                    Toast.makeText(BadGiveActivity.this,"Added Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(BadGiveActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
