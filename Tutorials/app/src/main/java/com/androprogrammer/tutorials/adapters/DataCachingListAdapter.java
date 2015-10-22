package com.androprogrammer.tutorials.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.models.UserDataResponse;
import com.androprogrammer.tutorials.util.Common;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Wasim on 24-09-2015.
 */
public class DataCachingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context ctx;
    private List<UserDataResponse> data;

    public DataCachingListAdapter(Context context, List<UserDataResponse> mData) {

        this.ctx = context;
        this.data = mData;
    }

    private class Viewholder extends RecyclerView.ViewHolder {

        protected TextView userName;
        protected TextView userEmail;
        protected TextView userWebsite;
        protected ImageView icon_mail,icon_website;

    public Viewholder(View v) {
        super(v);
        userName = (TextView) v.findViewById(R.id.row_tv_userName);
        //planDuration = (TextView) v.findViewById(R.id.row_tv_timeperiad);
        userEmail = (TextView) v.findViewById(R.id.row_tv_email);
        userWebsite = (TextView) v.findViewById(R.id.row_userWebsite);

        icon_mail = (ImageView) v.findViewById(R.id.row_icon_mail);
        icon_website = (ImageView) v.findViewById(R.id.row_icon_web);
    }
}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.row_datacaching_item, parent, false);

        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserDataResponse row_data = getItem(position);

        ((Viewholder) holder).userName.setText(row_data.getName());
        ((Viewholder) holder).userEmail.setText(row_data.getEmail());


        ((Viewholder) holder).userWebsite.setText(row_data.getWebsite());

        int color = Common.getThemeColor(ctx, R.attr.colorAccent);

        ((Viewholder) holder).icon_mail.setColorFilter(color,
                                                           android.graphics.PorterDuff.Mode.MULTIPLY);

        ((Viewholder) holder).icon_website.setColorFilter(color,
                                                           android.graphics.PorterDuff.Mode.MULTIPLY);

    }

    public UserDataResponse getItem(int pos)
    {
        return data.get(pos);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
