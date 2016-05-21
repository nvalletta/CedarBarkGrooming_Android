package com.cedarbarkgrooming.ui.gallery;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cedarbarkgrooming.R;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

public class GridBaseAdapter extends BaseAdapter {

    @Inject
    Integer [] mThumbnailIds;

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public GridBaseAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        getInjector().inject(this);
    }

    @Override
    public int getCount() {
        return mThumbnailIds.length;
    }

    @Override
    @IntegerRes
    public Integer getItem(int position) {
        return mThumbnailIds[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.item_grid, parent, false);
            view.setTag(R.id.image_square, view.findViewById(R.id.image_square));
            view.setTag(R.id.text, view.findViewById(R.id.text));
        }
        ImageView imageView = (ImageView) view.getTag(R.id.image_square);
        Picasso.with(mContext).load(mThumbnailIds[position]).fit().into(imageView);

        return view;
    }
}
