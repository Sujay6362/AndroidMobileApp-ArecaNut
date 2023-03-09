package com.areca.arecanut;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

public class AlaramHandler {

    private final Context context;
    public AlaramHandler(Context context){
        this.context = context;
    }

    public  void setAlaramManager(){
        Intent intent = new Intent(context,WidgetService.class);
        PendingIntent sender;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            sender = PendingIntent.getBroadcast(context,2,intent,PendingIntent.FLAG_IMMUTABLE);

        }
        else
        {
            sender = PendingIntent.getBroadcast(context,2,intent,0);

        }

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar c = Calendar.getInstance();
        long l = c.getTimeInMillis() + 10000;

        if(am != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, l,sender);
            }
            else
            {
                am.set(AlarmManager.RTC_WAKEUP,l,sender);
            }

        }
    }

    public void cancelAlaramManager(){

        Intent intent = new Intent(context,WidgetService.class);
        PendingIntent sender;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            sender = PendingIntent.getBroadcast(context,2,intent,PendingIntent.FLAG_IMMUTABLE);

        }
        else
        {
            sender = PendingIntent.getBroadcast(context,2,intent,0);

        }

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if(am != null){
            am.cancel(sender);
        }
    }

}
