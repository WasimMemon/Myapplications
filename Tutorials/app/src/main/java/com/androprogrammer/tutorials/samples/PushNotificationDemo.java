package com.androprogrammer.tutorials.samples;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;
import com.androprogrammer.tutorials.customviews.CircularImageView;

public class PushNotificationDemo extends Baseactivity implements View.OnClickListener {

    protected View view;
    protected Button notification_small, notification_big;
    protected EditText heading, message;
    protected String heading_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(android.view.Window.FEATURE_CONTENT_TRANSITIONS);

            //set the transition
            Transition ts = new Slide();
            ts.setDuration(3000);
            getWindow().setEnterTransition(ts);
            getWindow().setExitTransition(ts);

        }

        super.onCreate(savedInstanceState);

        setReference();

        setSimpleToolbar(true);

        setToolbarSubTittle(this.getClass().getSimpleName());

        setToolbarElevation(getResources().getDimension(R.dimen.elevation_normal));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void setReference() {

        view = LayoutInflater.from(this).inflate(R.layout.activity_pushnotification_demo, container);

        // TO set Reference to the xml button & edit text object.
        notification_small = (Button) view.findViewById(R.id.nd_smallnotification);
        notification_big = (Button) view.findViewById(R.id.nd_bignotification);

        notification_big.setOnClickListener(this);
        notification_small.setOnClickListener(this);

        heading = (EditText) view.findViewById(R.id.nd_etHeading);
        message = (EditText) view.findViewById(R.id.nd_notificationbody);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(PushNotificationDemo.this, CircularImageViewDemo.class);
        final PendingIntent pending = PendingIntent.getActivity(getApplicationContext(), 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Notification n;

        switch (v.getId()) {
            case R.id.nd_smallnotification:

                if (heading.getText().length() <= 0) {
                    heading.setError("Please provide push notification title");
                } else {
                    heading.setError(null);
                    heading_text = heading.getText().toString();
                    n = new NotificationCompat.Builder(this)
                            .setContentTitle(heading_text)
                            .setContentText(message.getText().toString())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(pending)
                            .setDefaults(Notification.DEFAULT_SOUND)
                            .setAutoCancel(true)
                            .setWhen(System.currentTimeMillis())
                            .addAction(android.R.drawable.ic_menu_manage, "Close", pending)
                            .build();

                    n.flags |= Notification.FLAG_AUTO_CANCEL | Intent.FLAG_ACTIVITY_SINGLE_TOP;
                    notificationManager.notify(0, n);
                }


                break;

            case R.id.nd_bignotification:

                if (heading.getText().length() <= 0) {
                    heading.setError("Please provide push notification title");
                } else {
                    heading.setError(null);
                    heading_text = heading.getText().toString();
                    NotificationCompat.InboxStyle nc =
                            new NotificationCompat.InboxStyle();

                    nc.setBigContentTitle(heading_text);

                    String[] text = new String[5];

                    for (int i = 0; i < 5; i++) {
                        text[i] = i + ". say hello...";
                        nc.addLine(text[i]);
                    }

                    n = new NotificationCompat.Builder(this)
                            .setStyle(nc)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(pending)
                            .setDefaults(Notification.DEFAULT_SOUND)
                            .setAutoCancel(true)
                            .setWhen(System.currentTimeMillis())
                            .build();

                    n.flags |= Notification.FLAG_AUTO_CANCEL | Intent.FLAG_ACTIVITY_SINGLE_TOP;

                    notificationManager.notify(100, n);
                }

                break;
        }
    }
}
