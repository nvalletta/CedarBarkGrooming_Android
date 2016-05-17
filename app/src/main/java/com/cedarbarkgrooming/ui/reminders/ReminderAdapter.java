package com.cedarbarkgrooming.ui.reminders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cedarbarkgrooming.model.reminders.Reminder;

import java.util.List;

import javax.inject.Inject;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

/**
 * Created by Nora on 5/14/2016.
 */
public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ReminderItem mReminderItem;

        public ViewHolder(ReminderItem v) {
            super(v);
            mReminderItem = v;
        }
    }

    @Inject
    List<Reminder> mReminders;

    public ReminderAdapter() {
        getInjector().inject(this);
    }

    @Override
    public int getItemCount() {
        return mReminders.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = new ReminderItem(parent.getContext());
        return new ReminderAdapter.ViewHolder((ReminderItem)view);
    }

    @Override
    public void onBindViewHolder(ReminderAdapter.ViewHolder holder, int position) {
        holder.mReminderItem.setReminder(mReminders.get(position));
    }

}
