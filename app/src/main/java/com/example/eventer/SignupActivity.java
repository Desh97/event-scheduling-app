package com.example.eventer;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText _s_name,_s_email,_s_pw,_s_num;
    private Button _btn_crate_account;

    String name,email,password,number;

    private TextView _s_txt;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        _s_name = (EditText)findViewById(R.id.s_name);
        _s_email = (EditText)findViewById(R.id.s_email);
        _s_pw = (EditText)findViewById(R.id.s_pw);
        _s_num = (EditText)findViewById(R.id.s_num);
        _s_txt = (TextView)findViewById(R.id.s_txt);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");


        _btn_crate_account = (Button)findViewById(R.id.btn_crate_account);

        _btn_crate_account.setOnClickListener(this);

        _s_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

    }

    private void registerUser(){
         name = _s_name.getText().toString().trim();
         email = _s_email.getText().toString().trim();
         password = _s_pw.getText().toString().trim();
         number = _s_num.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"please enter name",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(number)){
            Toast.makeText(this,"please enter mobile number",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Loading");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            sedUserData();
                            Toast.makeText(SignupActivity.this,"DONE!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SignupActivity.this,"TRY AGAIN!",Toast.LENGTH_LONG).show();
                        }
                    }
                });



    }


    @Override
    public void onClick(View view) {
        if( view ==  _btn_crate_account){
            registerUser();
        }
    }

    private void sedUserData(){


        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        Signup signup = new Signup(name,email,number);
        databaseReference.child(mFirebaseUser.getUid()).push().setValue(signup);

    }

}
