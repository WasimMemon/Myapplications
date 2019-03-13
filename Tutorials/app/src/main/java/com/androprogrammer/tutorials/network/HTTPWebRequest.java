package com.androprogrammer.tutorials.network;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.androprogrammer.tutorials.MainController;
import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.customviews.IonCircleTransform;
import com.androprogrammer.tutorials.listners.ApiResponse;
import com.androprogrammer.tutorials.util.Common;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class HTTPWebRequest {


    // Wait this many milliseconds max for the TCP connection to be established
    private static final int CONNECTION_TIMEOUT = 60 * 60 * 1000;

    // Wait this many milliseconds max for the server to send us data once the connection has been established
    private static final int SOCKET_TIMEOUT = 30000;

    private static final String TAG = "HTTPWebRequest";

    private static JsonObject createJsonObject(Object model) {
        JsonObject jsonObj = new JsonObject();
        Gson gsonBuilder = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();
        String abc = gsonBuilder.toJson(model);

        Gson gson2 = new Gson();
        /*JsonElement element = gson2.fromJson(abc, JsonElement.class);

        jsonObj.add(AppConstants.ResponseKey.Data, element);

        jsonObj.addProperty(AppConstants.ResponseKey.Token,
                AppConfig.preferenceGetString(AppConstants.ResponseKey.Token, ""));*/

        Log.d(TAG, "Service Request:-" + jsonObj.toString());
        return jsonObj;
    }


    public static void createJsonRequest(final Context ctx, final int apiCode,
                                         String url, String method, final ApiResponse listner)
    {

        Log.d(TAG, "URL:-" + url);
        if (Common.isConnectivityAvailable(ctx)) {

            Ion.with(ctx)
                    .load(method, url)
                    .setLogging("MyLogs", Log.VERBOSE)
                    .setTimeout(CONNECTION_TIMEOUT)
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {

                            if (e != null) {
                                listner.responseError(apiCode, e.getMessage());
                            } else {
                                Log.d(TAG, "Service Response:-" + result.toString());

                                listner.NetworkRequestCompleted(apiCode, result);


                            }
                        }
                    });
        } else {
            Common.showToast(ctx, ctx.getResources().getString(R.string.msg_noInternet));
        }
    }

    public static void CancellAllRequest(Context ctx) {
        // cancel all current requests...
        Ion.getDefault(ctx).cancelAll(ctx);
    }


    public static void setImageFromURL(ImageView iv, String ImageURL, int height, int width, int borderRadious) {
        Log.d(TAG,ImageURL);

        Ion.with(MainController.getAppInstance())
                .load(ImageURL)
                .setLogging("MyLogs", Log.VERBOSE)
                .setTimeout(500000)
                .withBitmap()
                .resize(height, width)
                .centerCrop()
                .transform(new IonCircleTransform(borderRadious))
                .placeholder(R.mipmap.user_noimage)
                .error(R.mipmap.user_noimage)
                .intoImageView(iv);
    }
}
