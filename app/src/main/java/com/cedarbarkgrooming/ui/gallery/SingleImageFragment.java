package com.cedarbarkgrooming.ui.gallery;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cedarbarkgrooming.R;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

public class SingleImageFragment extends Fragment {

    public static final String ARG_POSITION = "positionSelected";

    int mPosition;

    @Inject
    Integer [] mThumbnailIds;

    @BindView(R.id.image)
    ImageView mImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInjector().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (null != args) {
            mPosition = args.getInt(ARG_POSITION, -1);
            Activity activity = getActivity();
            if (null != activity && mPosition > -1) {
                Context context = getActivity().getApplicationContext();
                if (null != context) {
                    Picasso.with(context).load(mThumbnailIds[mPosition]).into(mImage);
                }
            }
        }
    }
}