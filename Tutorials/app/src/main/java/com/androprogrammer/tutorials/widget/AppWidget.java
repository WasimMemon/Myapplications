package com.androprogrammer.tutorials.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.androprogrammer.tutorials.R;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider
{

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;

        if (N > 0)
        {
            Toast.makeText(context,"widget already created...", Toast.LENGTH_LONG).show();
        }
        else
        {
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            views.setTextViewText(R.id.title, getWordMeaning(R.id.title));
            views.setTextViewText(R.id.desc, getWordMeaning(R.id.desc));

            // register for button event
            views.setOnClickPendingIntent(R.id.sync_button,
                    buildButtonPendingIntent(context));

            updateAppWidget(context, appWidgetManager, views);
        }
        /*for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }*/
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                RemoteViews remoteViews) {

        ComponentName myWidget = new ComponentName(context,
                AppWidget.class);

        appWidgetManager.updateAppWidget(myWidget, remoteViews);

        // Instruct the widget manager to update the widget
       // appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static PendingIntent buildButtonPendingIntent(Context context)
    {
        Toast.makeText(context,"button clicked... ", Toast.LENGTH_LONG).show();
        return null;
    }

    protected static CharSequence getWordMeaning(int id)
    {
        CharSequence text;

        switch (id)
        {
            case R.id.title:
                text = "Title";
                break;

            case R.id.desc:
                text = "Description";
                break;

            default:
                text = "error...";
                break;
        }

        return text;
    }
}


