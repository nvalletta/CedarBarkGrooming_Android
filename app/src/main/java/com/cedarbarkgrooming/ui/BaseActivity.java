package com.cedarbarkgrooming.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import icepick.Icepick;

/**
 * Created by Nora on 5/7/2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements PresentedView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        inject();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    protected abstract void inject();

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dismiss() {
        onBackPressed();
    }

}
