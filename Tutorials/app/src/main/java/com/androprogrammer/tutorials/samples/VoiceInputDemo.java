package com.androprogrammer.tutorials.samples;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;
import com.androprogrammer.tutorials.util.Common;

import java.util.ArrayList;
import java.util.List;

public class VoiceInputDemo extends Baseactivity {

    protected View view;
    private EditText word;
    private ImageButton bt_voiceinput;
    private static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

            //set the transition
            Transition ts = new Explode();
            ts.setDuration(5000);
            getWindow().setEnterTransition(ts);
            getWindow().setExitTransition(ts);
        }

        super.onCreate(savedInstanceState);
        setReference();

        setSimpleToolbar(true);

        setToolbarElevation(getResources().getDimension(R.dimen.elevation_normal));

        setToolbarSubTittle(this.getClass().getSimpleName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Disable button if no recognition service is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            bt_voiceinput.setEnabled(false);
        }

        bt_voiceinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognitionActivity();
            }
        });
        word.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bt_voiceinput.setEnabled(false);
            }
        });

    }

    @Override
    public void setReference() {

        view = LayoutInflater.from(this).inflate(R.layout.activity_voiceinput_demo,container);
        bt_voiceinput = (ImageButton) view.findViewById(R.id.ib_speak);
        word = (EditText) view.findViewById(R.id.et_word);
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
                // NavUtils.navigateUpFromSameTask(this);
                // return true;
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Fire an intent to start the voice recognition activity.
     */
    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak the word");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If Voice recognition is successful then it returns RESULT_OK
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> textMatchList = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (!textMatchList.isEmpty()) {
                    String Query = textMatchList.get(0);
                    word.setText(Query);
                }
                //Result code for various error.
            } else if (resultCode == RecognizerIntent.RESULT_NETWORK_ERROR) {
                Common.showToast(this, "Network Error");
            } else if (resultCode == RecognizerIntent.RESULT_NO_MATCH) {
                Common.showToast(this, "No Match");
            } else if (resultCode == RecognizerIntent.RESULT_SERVER_ERROR) {
                Common.showToast(this, "Server Error");
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
