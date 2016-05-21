package com.cedarbarkgrooming.ui.gallery;

import android.os.Bundle;
import android.widget.GridView;

import com.cedarbarkgrooming.R;
import com.cedarbarkgrooming.ui.BaseActivity;

import butterknife.BindView;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

public class GalleryActivity extends BaseActivity {

    @BindView(R.id.grid_before_and_afters)
    GridView mGridBeforeAndAfters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
//        mGridBeforeAndAfters.setAdapter(new GridBaseAdapter());
    }

    @Override
    protected void inject() {
        getInjector().inject(this);
    }

}
