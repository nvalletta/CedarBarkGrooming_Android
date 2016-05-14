package com.cedarbarkgrooming.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cedarbarkgrooming.R;
import com.cedarbarkgrooming.ui.BaseActivity;
import com.cedarbarkgrooming.ui.reminders.RemindersActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

public class HomeActivity extends BaseActivity {

    @Inject
    HomePresenter mHomePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void inject() {
        getInjector().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab_navigate)
    public void onNavigateClicked() {
        mHomePresenter.onNavigateClick();
    }

    @OnClick(R.id.button_description)
    public void onViewGalleryClicked() {
        mHomePresenter.onViewImageGalleryClick();
    }

    @OnClick(R.id.layout_reminders)
    public void onManageRemindersClicked() {
        mHomePresenter.onManageRemindersClick();
        startActivity(new Intent(this, RemindersActivity.class));
    }

}
