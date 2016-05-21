package com.cedarbarkgrooming.ui.gallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cedarbarkgrooming.R;

public class GridBaseAdapter extends BaseAdapter {

    private Integer[] mThumbnailIds = {
            R.drawable.aww_before, R.drawable.aww_after,
            R.drawable.fuzzyschnau_before, R.drawable.fuzzyschnau_after,
            R.drawable.hairy_before, R.drawable.hairy_after,
            R.drawable.happydog_before, R.drawable.happydog_after,
            R.drawable.maltese_before, R.drawable.maltese_after,
            R.drawable.mix_before, R.drawable.mix_after,
            R.drawable.poodly_before, R.drawable.poodly_after,
            R.drawable.schnauser_before, R.drawable.schnauser_after,
            R.drawable.scruffypup_before, R.drawable.scruffypup_after,
            R.drawable.shaggy_before, R.drawable.shaggy_after,
            R.drawable.shihtzu_before, R.drawable.shihtzu_after
    };

    private Context mContext;

    public GridBaseAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mThumbnailIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbnailIds[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ImageView imageView;
        TextView name;

//        if (view == null) {
//            view = mInflater.inflate(R.layout.grid_item, viewGroup, false);
//            view.setTag(R.id.picture, view.findViewById(R.id.picture));
//            view.setTag(R.id.text, view.findViewById(R.id.text));
//        }
//
//        imageView = (ImageView) view.getTag(R.id.picture);
//        name = (TextView) view.getTag(R.id.text);
//
//        Item item = getItem(i);
//
//        imageView.setImageResource(item.drawableId);
//        name.setText(item.name);

        return view;
    }
}
