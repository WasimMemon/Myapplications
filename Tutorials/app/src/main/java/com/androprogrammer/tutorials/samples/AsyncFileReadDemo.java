package com.androprogrammer.tutorials.samples;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;
import com.androprogrammer.tutorials.util.Common;
import com.androprogrammer.tutorials.util.ExceptionHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AsyncFileReadDemo extends Baseactivity {

    protected TextView content;
    protected EditText text;
    protected Button ok;
    protected static String FILENAME = "test.txt";
    protected File file;
    protected View view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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

        //Common.showToast(this, ExceptionHandler.getInstance(this).getString());

        /*int a = 0, b = 3;
        float c = b/a;*/

        setReference();

        setSimpleToolbar(true);

        setToolbarElevation(getResources().getDimension(R.dimen.elevation_normal));

        setToolbarSubTittle(this.getClass().getSimpleName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        file = new File(getFilesDir(), FILENAME);

        try {
            if (file.createNewFile()) {
                Toast.makeText(getApplicationContext(), "File Created",
                        Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Can't create file",
                        Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = text.getText().toString();
                text.setText("");

                readFromFile rf = new readFromFile(file);
                rf.execute(value);
            }
        });
    }

    @Override
    public void setReference() {

        view = LayoutInflater.from(this).inflate(R.layout.activity_asyncfile_read,container);
        content = (TextView) view.findViewById(R.id.textView);
        text = (EditText) view.findViewById(R.id.editText);
        ok = (Button) view.findViewById(R.id.button);
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

    private class readFromFile extends AsyncTask<String, Integer, String> {

        // static String FILENAME = "test.txt";
        File f;

        public readFromFile(File f) {
            super();
            this.f = f;
            // TODO Auto-generated constructor stub
        }

        @Override
        protected String doInBackground(String... str) {
            String enter = "\n";
            FileWriter writer = null;
            try {
                writer = new FileWriter(f,true);
                writer.append(str[0]);
                writer.append(enter);
                writer.flush();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String name = "";
            StringBuilder sb = new StringBuilder();
            FileReader fr = null;

            try {
                fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                while ((name = br.readLine()) != null) {
                    sb.append(name);
                }
                br.close();
                fr.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            content.setText(sb.toString());
        }

    }
}
