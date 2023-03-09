package com.areca.arecanut;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

public class MyWidget extends AppWidgetProvider
{

    void updateAppwidget(Context context, AppWidgetManager appWidgetManager, int appWidgetIds){
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget_design);

        SharedPreferences preferences = context.getSharedPreferences("PREFS",0);
        String str = preferences.getString("str",null);
        int value = preferences.getInt("value",1);


        views.setTextViewText(R.id.example_widget, ""+str);


        appWidgetManager.updateAppWidget(appWidgetIds,views);



        AlaramHandler alaramHandler = new AlaramHandler(context);
        alaramHandler.cancelAlaramManager();
        alaramHandler.setAlaramManager();

        Log.d("WIDGET","Widget updated!");

    }




    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
       for(int appWidgetID: appWidgetIds){
           updateAppwidget(context,appWidgetManager,appWidgetID);
       }
    }

    @Override
    public void onDisabled(Context context) {
        AlaramHandler alaramHandler = new AlaramHandler(context);
        alaramHandler.cancelAlaramManager();


        Log.d("WIDGET","Widget removed!");
    }
}
