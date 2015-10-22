package com.androprogrammer.tutorials.util;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.androprogrammer.tutorials.R;

/**
 * Created by Wasim on 11-09-2015.
 */
public class GridLayoutDivider extends RecyclerView.ItemDecoration {

    private int mItemOffset;

    public GridLayoutDivider(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public GridLayoutDivider(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
    }
}
