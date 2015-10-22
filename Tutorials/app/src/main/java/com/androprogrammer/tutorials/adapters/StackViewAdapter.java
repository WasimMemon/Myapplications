package com.androprogrammer.tutorials.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.androprogrammer.tutorials.R;

import java.util.List;

/**
 * Created by Wasim on 11-Oct-15.
 */
public class StackViewAdapter extends BaseAdapter {

    private Context contxt;
    private List<Integer> imageList;

    public StackViewAdapter(Context contxt, List<Integer> imageList) {
        this.contxt = contxt;
        this.imageList = imageList;
    }

    private class Viewholder
    {
        protected ImageView row_img;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int i) {
        return imageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Viewholder holder = null;

        if (view == null)
        {
            holder = new Viewholder();
            view = LayoutInflater.from(contxt).inflate(R.layout.row_stackview_item,null);
            holder.row_img = (ImageView) view.findViewById(R.id.row_stack_imageView);

            view.setTag(holder);
        }
        else
        {
            holder = (Viewholder) view.getTag();
        }

        holder.row_img.setImageResource(imageList.get(i));

        return view;
    }
}
