package com.cedarbarkgrooming.ui.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.cedarbarkgrooming.R;
import com.cedarbarkgrooming.model.reminders.Reminder;

import java.util.List;

import javax.inject.Inject;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

public class ReminderWidgetViewsFactory implements RemoteViewsFactory {

    @Inject
    List<Reminder> mReminders;

    Context mContext;

    public ReminderWidgetViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        getInjector().inject(this);
    }

    @Override
    public void onDataSetChanged() {
        //no-op
    }

    @Override
    public void onDestroy() {
        //no-op
    }

    @Override
    public int getCount() {
        return mReminders.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.item_reminder);

        row.setTextViewText(R.id.text_reminder_title, mReminders.get(position).getTitle());
        row.setTextViewText(R.id.text_reminder_date, mReminders.get(position).getDate().toString());

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
