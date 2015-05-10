package com.androprogrammer.tutorials.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by Kaira on 23-04-2015.
 */
public class CustomListView extends ListView implements AbsListView.OnScrollListener {

    private boolean controlsVisible = true;
    private ScrollObserver scrolllistner;
    private int mLastFirstVisibleItem;


    private static String TAG = "CustomListView";

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOnScrollListener(this);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnScrollListener(this);
    }

    public CustomListView(Context context) {
        super(context);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    public void setListener(ScrollObserver listener)
    {
        this.scrolllistner = listener;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {

        Log.d(TAG,"- visibleItemCount - " + visibleItemCount + "-firstVisibleItem-" + firstVisibleItem );

        if (firstVisibleItem == 0)
        {
            if(!controlsVisible) {
                scrolllistner.onShow();
                controlsVisible = true;
            }
        }else {

            if (mLastFirstVisibleItem < firstVisibleItem) {
                scrolllistner.onHide();
                controlsVisible = false;
            }

            if (mLastFirstVisibleItem > firstVisibleItem) {
                scrolllistner.onShow();
                controlsVisible = true;
            }
            mLastFirstVisibleItem = firstVisibleItem;
        }

        /*onMainContentScrolled(firstVisibleItem <= ITEMS_THRESHOLD ? 0 : Integer.MAX_VALUE,
                lastFvi - firstVisibleItem > 0 ? Integer.MIN_VALUE :
                        lastFvi == firstVisibleItem ? 0 : Integer.MAX_VALUE
        );

        lastFvi = firstVisibleItem;*/




       /* if (scrolledDistance > HIDE_THRESHOLD && controlsVisible)
        {
            scrolllistner.onHide();
            controlsVisible = false;
            scrolledDistance = 0;
        }
        else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
            scrolllistner.onShow();
            controlsVisible = true;
            scrolledDistance = 0;
        }

        if((controlsVisible && firstVisibleItem > 0) || (!controlsVisible && firstVisibleItem < 0)) {
            scrolledDistance = firstVisibleItem;
            Log.d(TAG, "1scrolledDistance- " + scrolledDistance);
        }
*/
    }


    public interface ScrollObserver{

        public void onHide();
        public void onShow();

    }
}
