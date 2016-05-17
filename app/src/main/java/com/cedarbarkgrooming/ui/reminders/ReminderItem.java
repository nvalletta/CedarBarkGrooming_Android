package com.cedarbarkgrooming.ui.reminders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cedarbarkgrooming.R;
import com.cedarbarkgrooming.model.reminders.Reminder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

/**
 * Created by Nora on 5/14/2016.
 */
public class ReminderItem extends RelativeLayout {

    @Inject
    RemindersPresenter mRemindersPresenter;

    @BindView(R.id.text_reminder_title)
    TextView mTextTitleView;

    @BindView(R.id.text_reminder_date)
    TextView mTextDateView;

    private Reminder mReminder;

    public ReminderItem(Context context) {
        super(context);
        initialize(context);
    }

    public ReminderItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public ReminderItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(@NonNull Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_reminder, this, true);
        ButterKnife.bind(this);
        getInjector().inject(this);
    }

    public void setReminder(@Nullable Reminder reminder) {
        mReminder = reminder;
        if (null == reminder) return;
        mTextTitleView.setText(mReminder.getTitle());
        DateFormat df = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        mTextDateView.setText(df.format(mReminder.getDate()));
    }

    public void setTitle(@NonNull CharSequence title) {
        mTextTitleView.setText(title);
    }

    public void setDate(@NonNull Date date) {
        DateFormat df = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        mTextDateView.setText(df.format(date));
    }

    @OnClick(R.id.button_delete)
    public void onButtonDeleteClick() {
        mRemindersPresenter.onReminderDeleted(mReminder);
    }

}
