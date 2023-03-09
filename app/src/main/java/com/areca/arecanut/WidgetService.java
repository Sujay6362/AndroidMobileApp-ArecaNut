package com.areca.arecanut;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class WidgetService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        WakeLocker.acquire((context));

        SharedPreferences preferences = context.getSharedPreferences("PREFS",0);
        String str = preferences.getString("str",null);
        int value  = preferences.getInt("value",1);
        value++;
        SharedPreferences.Editor editor = preferences.edit();
       
        editor.putInt("value",value);
        editor.apply();



        Intent widgetIntent = new Intent(context,MyWidget.class);
        widgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context,MyWidget.class));
        widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        context.sendBroadcast(widgetIntent);

        Log.d("WIDGET","Widget set to update!");

        WakeLocker.release();

    }
}
