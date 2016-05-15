package com.cedarbarkgrooming.ui;

import android.content.Context;

/**
 * Created by Nora on 5/15/2016.
 */
public interface PresentedView {
    Context getViewContext();

    void showError(String errorMessage);

    void dismiss();
}
