package com.androprogrammer.tutorials.adapters;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.androprogrammer.tutorials.R;

import java.util.List;

/**
 * Created by Kaira on 12-05-2015.
 */
public class AppsListAdapter extends BaseAdapter
{
    List<PackageInfo> packageList;
    Context context;
    PackageManager packageManager;
    boolean[] itemChecked;

    public AppsListAdapter(Context context, List<PackageInfo> packageList, PackageManager packageManager)
    {
        this.context = context;
        this.packageList = packageList;
        this.packageManager = packageManager;
        itemChecked = new boolean[packageList.size()];
    }

    private class ViewHolder {
        TextView apkName, packagename;
        CheckBox cb_visiblity;
        ImageView appIcon;
    }

    @Override
    public int getCount() {
        return packageList.size();
    }

    @Override
    public Object getItem(int position) {
        return packageList.get(position);
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

            convertView = LayoutInflater.from(context).inflate(R.layout.row_applist_item,null);

            holder.apkName = (TextView) convertView.findViewById(R.id.row_tv_appname);
            holder.packagename = (TextView) convertView.findViewById(R.id.row_tv_packagename);
            holder.appIcon = (ImageView) convertView.findViewById(R.id.row_iv_appicon);
            holder.cb_visiblity = (CheckBox) convertView.findViewById(R.id.row_cb_visiblity);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        PackageInfo row_data = packageList.get(position);

        createRow(holder,row_data,position);

        return convertView;
    }

    public void createRow(final ViewHolder holder, PackageInfo row_data, final int position)
    {
        Drawable appIcon = packageManager
                .getApplicationIcon(row_data.applicationInfo);
        String appName = packageManager.getApplicationLabel(
                row_data.applicationInfo).toString();
        appIcon.setBounds(0, 0, 30, 30);

        String packageName = row_data.packageName;

        holder.apkName.setText(appName);
        holder.packagename.setText(packageName);
        holder.appIcon.setBackgroundDrawable(appIcon);

        if (itemChecked[position])
            holder.cb_visiblity.setChecked(true);
        else
            holder.cb_visiblity.setChecked(false);

        holder.cb_visiblity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (holder.cb_visiblity.isChecked())
                    itemChecked[position] = true;
                else
                    itemChecked[position] = false;
            }
        });
    }
}
