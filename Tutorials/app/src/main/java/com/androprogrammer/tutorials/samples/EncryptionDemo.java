package com.androprogrammer.tutorials.samples;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;
import com.androprogrammer.tutorials.util.JavaEncryption;

import java.io.IOException;
import java.security.GeneralSecurityException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EncryptionDemo extends Baseactivity {


    private View view;

    @Bind(R.id.edt_text)
    EditText edtText;
    @Bind(R.id.tv_result)
    TextView tvResult;
    @Bind(R.id.btn_decrypt)
    Button btnDecrypt;
    @Bind(R.id.cv_encryptedValue)
    CardView cvEncryptedValue;

    private static String secretKey = "androprogrammer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setReference();

        setSimpleToolbar(true);
        setToolbarElevation(getResources().getDimension(R.dimen.elevation_normal));

        setToolbarSubTittle(this.getClass().getSimpleName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void setReference() {

        view = LayoutInflater.from(this).inflate(R.layout.activity_encryption_demo, container);

        ButterKnife.bind(this, view);
    }

    @OnClick({R.id.btn_encrypt, R.id.btn_decrypt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_encrypt:

                encryptText();

                break;
            case R.id.btn_decrypt:

                decryptText();

                break;
        }
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
                overridePendingTransition(0,R.anim.activity_close_scale);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void encryptText() {

        if (TextUtils.isEmpty(edtText.getText().toString()))
        {
            edtText.setError("Please enter string to encrypt");
        }else {
            edtText.setError(null);
            JavaEncryption encryptionClassObject = new JavaEncryption();

            try {
                tvResult.setText(encryptionClassObject.encrypt(edtText.getText().toString(),secretKey));

                edtText.setText("");

                btnDecrypt.setEnabled(true);

                if (cvEncryptedValue.getVisibility() == View.GONE)
                    cvEncryptedValue.setVisibility(View.VISIBLE);

            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }catch(RuntimeException e)
            {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void decryptText() {

        JavaEncryption decryptionClassObject = new JavaEncryption();

        try {

            edtText.setText(decryptionClassObject.decrypt(tvResult.getText().toString(),secretKey));

            btnDecrypt.setEnabled(false);

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
