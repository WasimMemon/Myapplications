package com.androprogrammer.tutorials;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;

public class ListActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,WearableListView.ClickListener {

    private WearableListView mListView;

    private ArrayList<String> listItems;

    private TextView tv_header;

    Node mNode; // the connected device to send the message to
    GoogleApiClient mGoogleApiClient;
    private boolean mResolvingError=false;

    public static String SERVICE_CALLED_WEAR = "WearListClicked";
    public static String TAG = "WearListActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Connect the GoogleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        initializeListItems();
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mListView = (WearableListView) stub.findViewById(R.id.listView1);

                // This is our list header
                tv_header = (TextView) findViewById(R.id.tv_header);

                mListView.setAdapter(new MyAdapter(ListActivity.this, listItems));
                mListView.setClickListener(ListActivity.this);

            }
        });
    }

    private void initializeListItems() {

        listItems = new ArrayList<>();
        listItems.add("Async File Read");
        listItems.add("Battery Status");
        listItems.add("Volume Setting");
        listItems.add("Frame Animation");
        listItems.add("Video Player");
        listItems.add("Circular Image View");
        listItems.add("Track User Location");
        listItems.add("Take Image");
        listItems.add("Image Grid View");
        listItems.add("Image Switcher");
        listItems.add("Tabs with Toolbar");
        listItems.add("Icon Tabs with Toolbar");
        listItems.add("Push Notification");
        //listItems.add("Async File Read");


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mResolvingError) {
            mGoogleApiClient.connect();
        }
    }

    /*
     * Resolve the node = the connected device to send the message to
     */
    private void resolveNode() {

        Wearable.NodeApi.getConnectedNodes(mGoogleApiClient)
                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(NodeApi.GetConnectedNodesResult nodes) {
                        for (Node node : nodes.getNodes()) {
                    mNode = node;
                }
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
        resolveNode();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    /**
     * Send message to mobile handheld
     */
    private void sendMessage(String Key) {

        if (mNode != null && mGoogleApiClient!= null && mGoogleApiClient.isConnected()) {
            Log.d(TAG, "-- " + mGoogleApiClient.isConnected());
            Wearable.MessageApi.sendMessage(
                    mGoogleApiClient, mNode.getId(), SERVICE_CALLED_WEAR + "--" + Key, null).setResultCallback(

                    new ResultCallback<MessageApi.SendMessageResult>() {
                        @Override
                        public void onResult(MessageApi.SendMessageResult sendMessageResult) {

                            if (!sendMessageResult.getStatus().isSuccess()) {
                                Log.e(TAG, "Failed to send message with status code: "
                                        + sendMessageResult.getStatus().getStatusCode());
                            }
                        }
                    }
            );
        }else{
            //Improve your code
        }

    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        TextView view = (TextView) viewHolder.itemView.findViewById(R.id.row_tv_name);
        String Key = view.getText().toString();
        Log.d(TAG, Key);
        sendMessage(Key);
    }

    @Override
    public void onTopEmptyRegionClick() {
        Toast.makeText(this, "You tapped on Top empty area", Toast.LENGTH_SHORT).show();
    }


    private class MyAdapter extends WearableListView.Adapter {
        private final LayoutInflater mInflater;
        private ArrayList<String> data;

        private MyAdapter(Context context, ArrayList<String> listItems) {
            mInflater = LayoutInflater.from(context);
            data = listItems;
        }

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WearableListView.ViewHolder(
                    mInflater.inflate(R.layout.row_wear_list, null));
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
            TextView view = (TextView) holder.itemView.findViewById(R.id.row_tv_name);
            view.setText(data.get(position));
            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
