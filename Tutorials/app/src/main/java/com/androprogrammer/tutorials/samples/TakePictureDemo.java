package com.androprogrammer.tutorials.samples;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;
import com.androprogrammer.tutorials.util.Common;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class TakePictureDemo extends Baseactivity {

    protected View view;
    protected ImageView imgViewCamera, img_compressed;
    protected int LOAD_IMAGE_CAMERA = 0, CROP_IMAGE = 1, LOAD_IMAGE_GALLARY = 2;
    private Uri picUri;
    private File pic;

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
        view = LayoutInflater.from(this).inflate(R.layout.activity_takepicture_demo, container);
        imgViewCamera = (ImageView) view.findViewById(R.id.img_camera);
        img_compressed = (ImageView) view.findViewById(R.id.img_compress);

    }

    public void takePicture_Click(View v) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(TakePictureDemo.this);
        builder.setTitle("Select Pic Using...");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                // TODO Auto-generated method stub
                if (options[item].equals("Take Photo")) {

                    try {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                        pic = new File(Environment.getExternalStorageDirectory(),
                                "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg");

                        picUri = Uri.fromFile(pic);

                        cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, picUri);

                        cameraIntent.putExtra("return-data", true);
                        startActivityForResult(cameraIntent, LOAD_IMAGE_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE_GALLARY);
                }
            }
        });

        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOAD_IMAGE_CAMERA && resultCode == RESULT_OK) {
            //if (data != null) {
                /*picUri = data.getData();

                if (picUri != null)
                    CropImage();*/

               /* Bundle extras = data.getExtras();
                Log.d("onActivityResult", "Inside on activity for result");
                Bitmap photo = (Bitmap) extras.get("data");
                //imgViewCamera.setImageBitmap(photo);
                picUri = getImageUri(this, photo);*/
                CropImage();

            //}

        }
        else if (requestCode == LOAD_IMAGE_GALLARY) {
            if (data != null) {

                picUri = data.getData();
                CropImage();
                // get the returned data
               /* Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    picUri = getImageUri(this, photo);
                    CropImage();
                }*/
            }
        }

        else if (requestCode == CROP_IMAGE) {
            if (data != null) {
                // get the returned data
                Bundle extras = data.getExtras();

                // get the cropped bitmap
                Bitmap photo = extras.getParcelable("data");

                imgViewCamera.setImageBitmap(photo);
                img_compressed.setImageBitmap(CompressResizeImage(photo));

                if (pic != null)
                {
                    // To delete original image taken by camera
                    if (pic.delete())
                        Common.showToast(TakePictureDemo.this,"original image deleted...");
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void CropImage() {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(picUri, "image/*");

            intent.putExtra("crop", "true");
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 3);
            intent.putExtra("aspectY", 4);
            intent.putExtra("scaleUpIfNeeded", true);
            intent.putExtra("return-data", true);
            //intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
            //intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);

            startActivityForResult(intent, CROP_IMAGE);

        } catch (ActivityNotFoundException e) {
            String errorMessage = "Your device doesn't support the crop action!";
            Common.showToast(this, errorMessage);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images
                .Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public Bitmap CompressResizeImage(Bitmap bm)
    {
        int bmWidth = bm.getWidth();
        int bmHeight = bm.getHeight();
        int ivWidth = imgViewCamera.getWidth();
        int ivHeight = imgViewCamera.getHeight();


        int new_height = (int) Math.floor((double) bmHeight *( (double) ivWidth / (double) bmWidth));
        Bitmap newbitMap = Bitmap.createScaledBitmap(bm, ivWidth, new_height, true);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        newbitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();

        Bitmap bm1 = BitmapFactory.decodeByteArray(b, 0, b.length);

        /*Bitmap output = Bitmap.createBitmap(ivWidth, ivHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Matrix m = new Matrix();
        m.setScale((float)ivWidth / bmWidth, (float)ivHeight/ bmHeight);
        canvas.drawBitmap(bm, m, new Paint());

        return output;*/
        return bm1;
    }
}
