package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText roll;
    Button reg;
    FirebaseAuth auth;
    DatabaseReference ref;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        pd=new ProgressDialog(this);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        reg=findViewById(R.id.register);
        roll=findViewById(R.id.roll_no);
        auth=FirebaseAuth.getInstance();
        ref= FirebaseDatabase.getInstance().getReference();
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email=email.getText().toString();
                String txt_roll=roll.getText().toString();
                String txt_password=password.getText().toString();

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_roll)){
                    Toast.makeText(RegisterActivity.this,"Empty Credentials!",Toast.LENGTH_SHORT).show();
                }
                else{
                    registerUser(txt_email,txt_roll,txt_password);
                }
            }
        });
    }

    private void registerUser(final String email,final String roll,String password) {
        pd.setMessage("Please Wait");
        pd.show();
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                HashMap<String, Object> map=new HashMap<>();
                map.put("email",email);
                map.put("roll no",roll);
                map.put("id",auth.getCurrentUser().getUid());
                ref.child("Users").child(auth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
