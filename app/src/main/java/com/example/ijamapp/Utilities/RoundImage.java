package com.example.ijamapp.Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * RoundImage Class
 */
public class RoundImage extends Drawable
{
    
    // Variables
    private final Bitmap mBitmap;
    private final Paint mPaint;
    private final RectF mRectF;
    private final int mBitmapWidth;
    private final int mBitmapHeight;
    
    
    /**
     * Constructors
     * @param bitmap Bitmap
     */
    public RoundImage(Bitmap bitmap)
    {
        mBitmap = bitmap;
        mRectF = new RectF();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        
        final BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
    
        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
    }
    
    /**
     * Draw method
     * @param canvas canvas object
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawOval(mRectF, mPaint);
    }
    
    /**
     * called when bounds change
     * @param bounds rect
     */
    @Override
    protected void onBoundsChange(Rect bounds)
    {
        super.onBoundsChange(bounds);
        mRectF.set(bounds);
    }
    
    /**
     * sets the alpha
     * @param alpha int
     */
    @Override
    public void setAlpha(int alpha)
    {
        if (mPaint.getAlpha() != alpha)
        {
            mPaint.setAlpha(alpha);
            invalidateSelf();
        }
    }
    
    /**
     * sers the color filter
     * @param cf color filter object
     */
    @Override
    public void setColorFilter(ColorFilter cf)
    {
        mPaint.setColorFilter(cf);
    }
    
    /**
     * gets the opacity
     * @return int
     */
    @Override
    public int getOpacity()
    {
        return PixelFormat.TRANSLUCENT;
    }
    
    /**
     * gets the intrinsic width
     * @return int
     */
    @Override
    public int getIntrinsicWidth()
    {
        return mBitmapWidth;
    }
    
    /**
     * gets the intrinsic height
     * @return int
     */
    @Override
    public int getIntrinsicHeight()
    {
        return mBitmapHeight;
    }
    
    /**
     * sets filter bitmap
     * @param filter boolean
     */
    @Override
    public void setFilterBitmap(boolean filter)
    {
        mPaint.setFilterBitmap(filter);
        invalidateSelf();
    }
    
    /**
     * gets the bitmap
     * @return Bitmap
     */
    public Bitmap getBitmap()
    {
        return mBitmap;
    }
}
