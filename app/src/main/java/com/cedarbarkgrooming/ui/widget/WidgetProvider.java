package com.cedarbarkgrooming.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.cedarbarkgrooming.R;
import com.cedarbarkgrooming.ui.reminders.RemindersActivity;

public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            Intent intent = new Intent(context, WidgetService.class);
            int widgetId = appWidgetIds[i];

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews widget = new RemoteViews(context.getPackageName(),
                    R.layout.widget_reminders);

            widget.setRemoteAdapter(R.id.list_reminders, intent);

            Intent clickIntent = new Intent(context, RemindersActivity.class);
            PendingIntent clickWidget = PendingIntent
                    .getActivity(context, 0,
                            clickIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            widget.setPendingIntentTemplate(R.id.list_reminders, clickWidget);
            appWidgetManager.updateAppWidget(widgetId, widget);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}
