package com.cedarbarkgrooming.ui.reminders;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cedarbarkgrooming.R;
import com.cedarbarkgrooming.ui.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

/**
 * Created by Nora on 5/14/2016.
 */
public class RemindersActivity extends BaseActivity {

    @Inject
    RemindersPresenter mRemindersPresenter;

    @BindView(R.id.recycler_reminders)
    RecyclerView mRecyclerReminders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        ButterKnife.bind(this);

        // todo: kick off content provider and loader stuff

        mRecyclerReminders.setAdapter(new ReminderAdapter());
    }

    @Override
    protected void inject() {
        getInjector().inject(this);
    }



}
