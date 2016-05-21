package com.cedarbarkgrooming.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cedarbarkgrooming.ui.widget.WidgetProvider;

/**
 * Created by Nora on 5/15/2016.
 */
public abstract class Presenter<V extends PresentedView> {

    protected V mPresentedView;

    public void setPresentedView(@NonNull V presentedView) {
        mPresentedView = presentedView;
    }

    @Nullable
    protected ContentResolver getContentResolver() {
        if (null == mPresentedView) return null;

        Context context = mPresentedView.getViewContext();
        if (null != context) {
            return context.getContentResolver();
        }
        return null;
    }

    protected void notifyRemoteViews() {
        Context context = mPresentedView.getViewContext();
        if (null != context) {
            AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
            ComponentName widgetComponent = new ComponentName(context, WidgetProvider.class);
            int[] widgetIds = widgetManager.getAppWidgetIds(widgetComponent);

            Intent intent = new Intent();
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds);
            intent.setPackage(context.getPackageName());
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            context.sendBroadcast(intent);
        }
    }

}
