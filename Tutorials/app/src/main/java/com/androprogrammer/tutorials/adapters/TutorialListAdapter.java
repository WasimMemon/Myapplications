package com.androprogrammer.tutorials.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.androprogrammer.tutorials.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wasim on 17-05-2015.
 */
public class TutorialListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private Context ctx;
    private List<String> data;
    private SparseBooleanArray selectedItems;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;


    public TutorialListAdapter(Context ctx, List<String> list)
    {
        this.ctx = ctx;
        this.data = list;
        this.selectedItems = new SparseBooleanArray();
    }

    class VHItem extends RecyclerView.ViewHolder {
        TextView tvName;


        public VHItem(View itemView) {
            super(itemView);
           // row_container = (LinearLayout) itemView.findViewById(R.id.row_container);
            tvName = (TextView) itemView.findViewById(R.id.row_tutorial_title);

        }
    }


    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.row_mainlist_item,
                        parent,
                        false);
        return new VHItem(itemView);
    }

    public Object getItem(int position) {
        return (String) data.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((VHItem) holder).tvName.setText((String) getItem(position));
    }


    public void add(int position, String value) {
        position = position == -1 ? getItemCount()  : position;
        data.set(position,value);
        notifyItemInserted(position);
    }

    public void remove(int position){
        if (position < getItemCount()  ) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void animateTo(List<String> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
    }

    private void applyAndAnimateAdditions(List<String> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final String model = newModels.get(i);
            if (!data.contains(model)) {
                add(i, model);
            }
        }
    }

    private void applyAndAnimateRemovals(List<String> newModels) {
        for (int i = data.size() - 1; i >= 0; i--) {
            final String model = data.get(i);
            if (!newModels.contains(model)) {
                remove(i);
            }
        }
    }

    public SparseBooleanArray getSelectedItem()
    {
        return selectedItems;
    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            //remove(pos);
            selectedItems.delete(pos);
        }
        else {
            //AppData tmpData = getItem(pos);
            //add(tmpData,pos);
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position, int type)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(ctx, android.R.anim.slide_in_left);
            animation.setDuration(500);
            viewToAnimate.startAnimation(animation);

            /*if (type == 1)
            {
                Animation animation = AnimationUtils.loadAnimation(ctx, android.R.anim.slide_in_left);
                viewToAnimate.startAnimation(animation);
            }
            else
            {
                Animation animation = AnimationUtils.loadAnimation(ctx, android.R.anim.slide_out_right);
                viewToAnimate.startAnimation(animation);
            }*/

            lastPosition = position;
        }
    }


    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<String> getSelectedItems() {
        List<String> items =
                new ArrayList<String>(selectedItems.size());


        return items;
    }
}
