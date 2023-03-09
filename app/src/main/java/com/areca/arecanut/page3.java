package com.areca.arecanut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class page3 extends AppCompatActivity {

       //String place="CHITRADURGA";
        String place="";
        String refreshstring;



    private static final String Shared_Pref_Name = "mypref";
    private static final String placeback = "place";
    private static final String Key_tc = "tc";
    private static final String Key_td = "td";
    private static final String Key_tr = "tr";
    private static final String Key_tf = "tf";
    private static final String Key_tg = "tg";

    SharedPreferences sharedPreferences;

    String url = "https://www.krishimaratavahini.kar.nic.in/MainPage/DailyMrktPriceRep2.aspx?Rep=Com&CommCode=140&VarCode=1&Date=12/10/2018&CommName=Arecanut%20/%20%E0%B2%85%E0%B2%A1%E0%B2%BF%E0%B2%95%E0%B3%86&VarName=Red%20/%20%E0%B2%95%E0%B3%86%E0%B2%82%E0%B2%AA%E0%B3%81";

    ProgressBar progressBar;
    TextView totalcases,totaldeaths,totalrecoversy,totalfinal,totalgrand;
    String tcases,tdeaths,trecovery,tfinal,tgrand;
    String tc,td,tr,tf,tg;
    Button btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        __init__();

        new Doingbackground().execute();

        sharedPreferences= getSharedPreferences(Shared_Pref_Name, MODE_PRIVATE);


    }


    public class Doingbackground extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
                Intent intent=getIntent();
                refreshstring=intent.getStringExtra("place");
                place=refreshstring;
                Log.d("refresh", "onPreExecute: "+refreshstring);

        }

        @Override
        protected void onPostExecute(Void unused) {
            progressBar.setVisibility(View.GONE);
            setvalues();

            super.onPreExecute();


            

           // Toolbar toolbar=findViewById(R.id.tollbar);
           // setSupportActionBar(toolbar);

           /* ConstraintLayout constraintLayout = findViewById(R.id.mainlayout);
            AnimationDrawable animationDrawable= (AnimationDrawable) constraintLayout.getBackground();
            animationDrawable.setEnterFadeDuration(1000);
            animationDrawable.setEnterFadeDuration(2000);
            animationDrawable.start();  */

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Goingback();
                }



            });



        // toobar / action bar

            Toolbar toolbar= findViewById(R.id.toolbar31);
            setSupportActionBar(toolbar);
            if(getSupportActionBar()!=null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setTitle("Pin This");

                //getSupportActionBar().setTitle("my toolbar");
            }


            super.onPostExecute(unused);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {


                Document document = Jsoup.connect(url).timeout(30*1000).userAgent("Mozilla/5.0 (Windows NT 6.1)").get();

                Elements element = document.select("table#_ctl0_content5_Table1");
                Elements rows = element.select("tr");
                System.out.println(rows.size());

                HashMap<Integer,String> map=new HashMap<Integer,String>();
                //ArrayList<String> arr=new ArrayList<>();


                int i=1;
                for(Element e:rows) {
                    //System.out.println(e.text());
                    if(i!=1) {
                        map.put(i,e.text());
                    }
                    i++;

                }



                int n=0;
                for ( Map.Entry<Integer, String> entry : map.entrySet()) {
                    int key = entry.getKey();
                    String tab = entry.getValue();
                    // do something with key and/or tab

                    if(tab.contains(place)) {
                        System.out.println("" +""+tab);
                        Log.d("abc", "doInBackground: "+tab);
                        if(n==0){
                            //tcases=tab;
                            totalcases.setText(makebold(tab));
                        }
                        else if(n==1){
                            totaldeaths.setText(makebold(tab));
                        }
                        else if(n==2){
                            totalrecoversy.setText(makebold(tab));
                        }
                        else if(n==3){
                            totalfinal.setText(makebold(tab));
                        }
                        else if(n==4){
                            totalgrand.setText(tab);
                        }

                        n++;

                    }


                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if(itemId == R.id.t1){

            PassingValue();
           Intent intent = new Intent(page3.this,page2.class);

            intent.putExtra("totalcases",tc);
            intent.putExtra("totaldeaths",td);
            intent.putExtra("totalrecovery",tr);
            intent.putExtra("totalfinal",tf);
            intent.putExtra("totalgrand",tg);





        //  saving data on pinned
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(placeback,place);
            editor.putString(Key_tc,tc);
            editor.putString(Key_td,td);
            editor.putString(Key_tr,tr);
            editor.putString(Key_tf,tf);
            editor.putString(Key_tg,tg);

            editor.apply();

            finish();
            startActivity(intent);

        }
        else{
            Toast.makeText(page3.this,"Already pinned",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt_menu,menu);
        //new MenuInflater(this).inflate(R.menu.opt_menu,menu);
        return super.onCreateOptionsMenu(menu);
        //return true;
    }


    public void setvalues(){
        //totalcases.setText(tcases);
        /*totaldeaths.setText(tdeaths);
        totalrecoversy.setText(trecovery);
        totalfinal.setText(tfinal);
        totalgrand.setText(tgrand);*/

    }

    public void __init__(){
        totalcases = findViewById(R.id.totalcases);
        totaldeaths = findViewById(R.id.totaldeaths);
        totalrecoversy = findViewById(R.id.totalrecovery);
        totalfinal = findViewById(R.id.totalfinal);
        totalgrand=findViewById(R.id.totalgrand);
        progressBar = findViewById(R.id.progressbar);
        btn = findViewById(R.id.goback);


    }

    public void PassingValue(){

        tc = totalcases.getText().toString();
        td = totaldeaths.getText().toString();
        tr = totalrecoversy.getText().toString();
        tf = totalfinal.getText().toString();
        tg = totalgrand.getText().toString();

        Toast.makeText(page3.this,"Returning back",Toast.LENGTH_SHORT).show();


    }


    public void Goingback(){
        Toast.makeText(page3.this,"Returning back",Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(page3.this,page2.class));

    }
    public SpannableString makebold(String tab){
        Log.d("bbb", "doInBackground: "+tab);
        SpannableString spannableString=new SpannableString(tab);
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        StyleSpan mbold = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(bold,0,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }


}