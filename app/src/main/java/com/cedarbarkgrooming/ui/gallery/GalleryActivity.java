package com.cedarbarkgrooming.ui.gallery;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.GridView;

import com.cedarbarkgrooming.R;
import com.cedarbarkgrooming.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

public class GalleryActivity extends BaseActivity {

    private static final String IMAGE_FRAGMENT_NAME = "singleImageFragment";

    @BindView(R.id.grid_before_and_afters)
    GridView mGridBeforeAndAfters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        mGridBeforeAndAfters.setAdapter(new GridBaseAdapter(this));
    }

    @Override
    protected void inject() {
        getInjector().inject(this);
    }

    @OnItemClick(R.id.grid_before_and_afters)
    public void imageSelected(int position) {
        Fragment newFragment = new SingleImageFragment();

        Bundle args = new Bundle();
        args.putInt(SingleImageFragment.ARG_POSITION, position);
        newFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(android.R.id.content, newFragment).addToBackStack(IMAGE_FRAGMENT_NAME).commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        if (null != fragmentManager && fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}
