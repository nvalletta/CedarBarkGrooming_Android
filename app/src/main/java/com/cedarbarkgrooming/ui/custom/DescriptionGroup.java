package com.cedarbarkgrooming.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cedarbarkgrooming.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nora on 5/14/2016.
 */
public class DescriptionGroup extends RelativeLayout {

    @BindView(R.id.text_description)
    TextView mTextViewDescription;

    @BindView(R.id.button_description)
    Button mButtonDescription;

    public DescriptionGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public DescriptionGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    private void initialize(@NonNull Context context, @NonNull AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.item_description_group, this, true);
        ButterKnife.bind(this);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DescriptionGroup, 0, 0);
        try {
            populateViews(typedArray);
        } finally {
            typedArray.recycle();
        }
    }

    private void populateViews(TypedArray typedArray) {
        mTextViewDescription.setText(typedArray.getText(R.styleable.DescriptionGroup_description));
        CharSequence buttonText = typedArray.getText(R.styleable.DescriptionGroup_actionText);
        if (null == buttonText || buttonText.equals("")) {
            mButtonDescription.setVisibility(View.GONE);
        } else {
            mButtonDescription.setText(buttonText);
        }
    }

}
