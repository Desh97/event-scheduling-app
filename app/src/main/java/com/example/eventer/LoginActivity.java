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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText _log_email,_log_pw;
    private TextView _log_su,_log_txt;
    private Button _log_button;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       // if(firebaseAuth.getCurrentUser()!=null){
       //     finish();
      //      startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
       // }

        _log_email = (EditText)findViewById(R.id.log_email);
        _log_pw = (EditText)findViewById(R.id.log_pw);
        _log_su = (TextView) findViewById(R.id.sign_n);
        _log_button = (Button) findViewById(R.id.button);
        _log_txt = (TextView)findViewById(R.id.log_txt);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        _log_button.setOnClickListener(this);
        _log_su.setOnClickListener(this);

        _log_su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        _log_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,PasswordActivity.class);
                startActivity(intent);
            }
        });
    }


    private void userLogin(){

        String email = _log_email.getText().toString().trim();
        String password = _log_pw.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Loading");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this,"TRY AGAIN!",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view == _log_button){
            userLogin();
        }
    }
}
