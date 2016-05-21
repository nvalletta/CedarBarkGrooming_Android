package com.cedarbarkgrooming.ui.reminders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cedarbarkgrooming.R;
import com.cedarbarkgrooming.model.reminders.Reminder;
import com.cedarbarkgrooming.ui.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

/**
 * Created by Nora on 5/14/2016.
 */
public class RemindersActivity extends BaseActivity implements RemindersView {

    @Inject
    RemindersPresenter mRemindersPresenter;

    @Inject
    List<Reminder> mReminders;

    @BindView(R.id.recycler_reminders)
    RecyclerView mRecyclerReminders;

    ReminderAdapter mReminderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        ButterKnife.bind(this);
        mRemindersPresenter.setPresentedView(this);

        mReminderAdapter = new ReminderAdapter();
        mRecyclerReminders.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerReminders.setAdapter(mReminderAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRemindersPresenter.setRemindersChangeListener(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReminderAdapter.notifyDataSetChanged();
        mRemindersPresenter.setRemindersChangeListener(() -> mReminderAdapter.notifyDataSetChanged());
    }

    @Override
    protected void inject() {
        getInjector().inject(this);
    }

    @OnClick(R.id.button_description)
    public void onCreateNewReminderClick() {
        startActivityForResult(new Intent(this, CreateReminderActivity.class), RESULT_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mReminderAdapter.notifyDataSetChanged();
    }

}
