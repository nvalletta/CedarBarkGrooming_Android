package com.cedarbarkgrooming.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.cedarbarkgrooming.R;
import com.cedarbarkgrooming.ui.home.HomeActivity;

public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i = 0; i < appWidgetIds.length; i++) {
            int widgetId = appWidgetIds[i];
            RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.widget_reminders);

            Intent serviceIntent = new Intent(context, WidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));
            widget.setRemoteAdapter(R.id.widget_list_reminders, serviceIntent);

            Intent intent = new Intent(context, HomeActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            widget.setPendingIntentTemplate(R.id.widget_list_reminders, pendingIntent);

            appWidgetManager.updateAppWidget(widgetId, widget);
            appWidgetManager.notifyAppWidgetViewDataChanged(widgetId, R.id.widget_list_reminders);
        }
    }

}
