package com.androprogrammer.tutorials.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.androprogrammer.tutorials.R;

/**
 * Created by Kaira on 01-04-2015.
 */
public class ImageAdapter extends BaseAdapter
{
    private Context mContext;
    protected int[] DRAWABLES;
    protected String[] NAMES;


    public ImageAdapter(Context mContext, int[] DRAWABLES, String[] NAMES) {
        this.mContext = mContext;
        this.DRAWABLES = DRAWABLES;
        this.NAMES = NAMES;
    }

    private class ViewHolder {
        protected ImageView i;
        protected TextView title;
    }

    @Override
    public int getCount() {
        return DRAWABLES.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_imageanimation_grid,null);
            holder.i = (ImageView) convertView.findViewById(R.id.ducky);
            holder.title = (TextView) convertView.findViewById(R.id.row_tittle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.i.setImageResource(DRAWABLES[position]);
        holder.title.setText(NAMES[position]);

        return convertView;
    }
}
