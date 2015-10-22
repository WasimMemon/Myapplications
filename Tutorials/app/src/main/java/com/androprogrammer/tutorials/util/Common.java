package com.androprogrammer.tutorials.util;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.Toast;

import com.androprogrammer.tutorials.R;

/**
 * Created by Wasim on 29-06-2015.
 */
public class Common {

    public static class App_Network_values
    {
        public static final String METHOD_GET = "GET";
        public static final String METHOD_POST = "POST";

        public static final String Code = "code";
        public static final String Message = "message";

        public static final int Api_call_getData = 1;
        public static final int Api_call_Success = 200;

        //public static final String URL_APP_BASE = "http://10.0.3.2/Joomla_3.4.4/index.php?option=com_ijoomeradv";

        public static final String URL_APP_BASE = "http://jsonplaceholder.typicode.com/users";
    }

    public static void showToast(Context ctx, String message)
    {
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
    }

    public static int getThemeColor(Context c, int resId)
    {
        // To get Primary selector color runtime.
        TypedValue typedValue = new TypedValue();
        c.getTheme().resolveAttribute(resId, typedValue, true);
        return typedValue.data;
    }

    public static boolean isConnectivityAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }
}
