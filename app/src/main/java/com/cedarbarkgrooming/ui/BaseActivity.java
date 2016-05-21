package com.cedarbarkgrooming.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Nora on 5/7/2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements PresentedView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
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
