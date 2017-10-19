package com.example.thatnight.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.thatnight.view.R;

import java.util.List;

/**
 * Created by thatnight on 2017.10.14.
 */

public class ColorSpAdapter extends BaseAdapter {

    private List<String> mItems;

    private Context mContext;

    public ColorSpAdapter(Context context, List<String> items) {
        mItems = items;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_sp_color, null);
            holder.mTextView = view.findViewById(R.id.tv_sp_color);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.mTextView.setText(mItems.get(i));
        return view;
    }

    class ViewHolder {
        TextView mTextView;
    }
}
