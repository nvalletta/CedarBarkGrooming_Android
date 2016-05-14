package com.cedarbarkgrooming.ui.reminders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cedarbarkgrooming.R;
import com.cedarbarkgrooming.model.Reminder;

import java.util.List;

import javax.inject.Inject;

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

    @Override
    public int getItemCount() {
        return mReminders.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent);
        return new ViewHolder((ReminderItem)view);
    }

    @Override
    public void onBindViewHolder(ReminderAdapter.ViewHolder holder, int position) {
        Reminder reminder = mReminders.get(position);
        holder.mReminderItem.setTitle(reminder.getTitle());
        holder.mReminderItem.setDate(reminder.getDate());
    }
}
