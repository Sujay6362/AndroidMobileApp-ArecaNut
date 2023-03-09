package com.areca.arecanut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class page2 extends AppCompatActivity implements AdapterView.OnItemClickListener {

   //String place="BANTWALA";
    String tc,td,tr,tf,tg;

// choosing from list
    String item_chosen;
    String place;
   String [] items={"BANTWALA","BELTHANGADI","BHADRAVATHI","CHANNAGIRI","CHITRADURGA","DAVANAGERE","HOSANAGAR","HULIYAR","KARKALA","KOPPA","KUMTA","KUNDAPUR","MADIKERI","PUTTUR","SAGAR","SHIVAMOGGA","SIDDAPURA","SIRA","SIRSI","SORABHA","TARIKERE","TIRTHAHALLI","TUMAKURU","YELLAPURA"};
   AutoCompleteTextView autoCompleteTextView;
   ArrayAdapter<String> stringArrayAdapter;

    private static final String Shared_Pref_Name = "mypref";
    private static final String placeback = "place";
    private static final String Key_tc = "tc";
    private static final String Key_td = "td";
    private static final String Key_tr = "tr";
    private static final String Key_tf = "tf";
    private static final String Key_tg = "tg";
    SharedPreferences sharedPreferences;

    Toolbar toolbar;
    TextView r11_t1,r11_t2,r11_t3,r11_t4,r11_t5;



    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    Button out;
    Button bbt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);


        sharedPreferences = getSharedPreferences(Shared_Pref_Name,MODE_PRIVATE);



        //auto complete list
        autoCompleteTextView = findViewById(R.id.autoComplete);
        stringArrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTextView.setAdapter(stringArrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item_chosen = adapterView.getItemAtPosition(i).toString();


            }
        });




        //callfunction

        bbt=findViewById(R.id.bbt);
        bbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(page2.this,"Login Successful",Toast.LENGTH_SHORT).show();
                // creating a connection to next page
                //Intent intent = new Intent(page2.this,page3.class);
                //startActivity(intent);
                find(item_chosen);
            }
        });




        //Display Pinned Data in page2



        r11_t1=findViewById(R.id.r1_t1);
        r11_t2=findViewById(R.id.r1_t2);
        r11_t3=findViewById(R.id.r1_t3);
        r11_t4=findViewById(R.id.r1_t4);
        r11_t5=findViewById(R.id.r1_t5);

        Intent intent=getIntent();

        tc=intent.getStringExtra("totalcases");
        td=intent.getStringExtra("totaldeaths");
        tr=intent.getStringExtra("totalrecovery");
        tf=intent.getStringExtra("totalfinal");
        tg=intent.getStringExtra("totalgrand");


        r11_t1.setText(tc);
        r11_t2.setText(td);
        r11_t3.setText(tr);
        r11_t4.setText(tf);
        r11_t5.setText(tg);


    // display saved pinned data
        place = sharedPreferences.getString(placeback,null);
        String t1 = sharedPreferences.getString(Key_tc,null);
        String t2 = sharedPreferences.getString(Key_td,null);
        String t3 = sharedPreferences.getString(Key_tr,null);
        String t4 = sharedPreferences.getString(Key_tf,null);
        String t5 = sharedPreferences.getString(Key_tg,null);

        r11_t1.setText(t1);
        r11_t2.setText(t2);
        r11_t3.setText(t3);
        r11_t4.setText(t4);
        r11_t5.setText(t5);








        //setting toolbar
        toolbar=findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
           //getSupportActionBar().setTitle("my toolbar");
        }

        // linking google account
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        out = findViewById(R.id.out);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });





    }






    // navigation / toolbar operation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt_menu,menu);
        //new MenuInflater(this).inflate(R.menu.opt_menu,menu);
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.t1) {
            Toast.makeText(page2.this, "unpin successful", Toast.LENGTH_SHORT).show();
            UnPin();

        }

        else if(itemId == R.id.refresh) {
            find(place);

        }

        return super.onOptionsItemSelected(item);
    }

    public SpannableString makebold(String tab){
        Log.d("bbb", "doInBackground: "+tab);
        SpannableString spannableString=new SpannableString(tab);
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        StyleSpan mbold = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(bold,0,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }


/*--------------------------------------  Methods -------------------------------------------------------------*/

    public void UnPin(){

        place=null;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

        Intent intent = new Intent(page2.this,page2.class);
        startActivity(intent);

    }



    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Toast.makeText(page2.this,"signed out successfully",Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(page2.this,MainActivity.class));


                    }
                });
    }

    public void find() {

        Intent intent = new Intent(page2.this,page3.class);
        intent.putExtra("place",item_chosen);
        finish();
        startActivity(intent);
    }
    public void find(String item_chosen){
        Intent intent = new Intent(page2.this,page3.class);
        intent.putExtra("place",item_chosen);
        finish();
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
