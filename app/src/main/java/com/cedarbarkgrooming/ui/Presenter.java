package com.cedarbarkgrooming.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Nora on 5/15/2016.
 */
public abstract class Presenter<V extends PresentedView> {

    protected V mPresentedView;

    public void setPresentedView(@NonNull V presentedView) {
        mPresentedView = presentedView;
    }

    @Nullable
    protected ContentResolver getContentResolver() {
        if (null == mPresentedView) return null;

        Context context = mPresentedView.getViewContext();
        if (null != context) {
            return context.getContentResolver();
        }
        return null;
    }

}
