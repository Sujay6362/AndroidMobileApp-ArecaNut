package com.areca.arecanut;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
  
    private TextView textView;
    private EditText editText;

    GoogleSignInOptions gso;
    GoogleSignInClient  gsc;
    ImageView googlebtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);

        MaterialButton loginbuttom = (MaterialButton) findViewById(R.id.loginbuttom);







        // admin and admin

        loginbuttom.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin"))
                {
                    // correct
                    Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();



                    // creating a connection to next page
                     Intent intent = new Intent(MainActivity.this,page2.class);
                    startActivity(intent);
                    //navigate();



                }
                else
                {
                    Toast.makeText(MainActivity.this,"Login Failed !!!",Toast.LENGTH_SHORT).show();
                }

            }
        });










        // linking google as account

        googlebtn = findViewById(R.id.googlebtn);    // google image  -> id , then next page view
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);


        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        if(acc!=null){
            navigate();
        }

        googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


    }

    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       if(requestCode == 1000)
       {
           Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
           try{
               task.getResult(ApiException.class);
               navigate();
           }
           catch (ApiException e){
               Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_SHORT).show();
           }


       }

    }

    void navigate() {
        finish();
        Intent intent = new Intent(MainActivity.this,page2.class);
        startActivity(intent);
    }
}


















