package com.cedarbarkgrooming.ui.reminders;

import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import com.cedarbarkgrooming.R;
import com.cedarbarkgrooming.ui.BaseActivity;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

/**
 * Created by Nora on 5/14/2016.
 */
public class CreateReminderActivity extends BaseActivity{

    @Inject
    CreateReminderPresenter mCreateReminderPresenter;

    @BindView(R.id.edittext_title)
    EditText mEditTextReminderTitle;

    @BindView(R.id.datepicker)
    DatePicker mDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);
        ButterKnife.bind(this);
    }

    @Override
    protected void inject() {
        getInjector().inject(this);
    }

    @OnClick(R.id.button_done)
    public void onDoneClick() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mDatePicker.getYear(), mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
        mCreateReminderPresenter.createReminder(mEditTextReminderTitle.getText().toString(), calendar.getTime());

        onBackPressed();
    }

}
