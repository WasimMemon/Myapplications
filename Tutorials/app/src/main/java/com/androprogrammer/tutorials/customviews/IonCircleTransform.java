package com.androprogrammer.tutorials.customviews;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.koushikdutta.ion.bitmap.Transform;


/**
 * Created by Kaira on 02-09-2015.
 */
public class IonCircleTransform implements Transform {
    private static final int BORDER_COLOR = Color.WHITE;
    private static  int BORDER_RADIUS = 0;

    private int cornerRadius = 150;
    private boolean isOval = false;


    public IonCircleTransform(int borderRadious) {
        super();
		 BORDER_RADIUS = borderRadious;
    }

    @Override
    public Bitmap transform(Bitmap source)
    {
        if (source == null) return null;

        int size = Math.min(source.getWidth(), source.getHeight());

    /* int x = (source.getWidth() - size) / 2 */;
    /* int y = (source.getHeight() - size) / 2 */;

        int x = (source.getWidth() - size);
        int y = (source.getHeight() - size);

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
       /* if (squaredBitmap != source) {
            source.recycle();
        }*/

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f/* 2f */;
        Paint paintBg = new Paint();
        paintBg.setColor(BORDER_COLOR);
        paintBg.setAntiAlias(true);

        // Draw the background circle
        canvas.drawCircle(r, r, r, paintBg);

        // Draw the image smaller than the background so a little border will be seen
        canvas.drawCircle(r, r, r- BORDER_RADIUS, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "rounded_radius_" + cornerRadius + "_oval_" + isOval;
    }

}
