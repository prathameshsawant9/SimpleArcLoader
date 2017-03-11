package com.leo.simplearcloader;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by prathamesh on 16/01/16.
 */
public class SimpleArcLoader extends View implements Animatable
{
    // =============================================================================================
    // CONSTANTS
    // =============================================================================================

    private static long FRAME_DURATION = 1000 / 60;

    public static int MARGIN_NONE = 0;
    public static int MARGIN_MEDIUM = 5;
    public static int MARGIN_LARGE = 10;

    public static int SPEED_SLOW = 1;
    public static int SPEED_MEDIUM = 5;
    public static int SPEED_FAST = 10;

    public static int STROKE_WIDTH_DEFAULT = 10;

    public enum STYLE {SIMPLE_ARC, COMPLETE_ARC}

    // =============================================================================================
    // FIELDS
    // =============================================================================================

    ArcDrawable mArcDrawable;

    // =============================================================================================
    // CONSTRUCTORS
    // =============================================================================================

    public SimpleArcLoader(Context context) {
        super(context);
    }

    public SimpleArcLoader(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public SimpleArcLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public SimpleArcLoader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    // =============================================================================================
    // METHODS
    // =============================================================================================

    private void init(AttributeSet attributeSet) {
        ArcConfiguration configuration = readFromAttributes(attributeSet);

        // Load Drawable for this View
        mArcDrawable = new ArcDrawable(configuration,this);
        setBackgroundDrawable(mArcDrawable);

        // Start the Animation
        start();
    }

    private ArcConfiguration readFromAttributes(AttributeSet attributeSet) {
        ArcConfiguration configuration = new ArcConfiguration(getContext());

        TypedArray array = getContext().obtainStyledAttributes(attributeSet, R.styleable.SimpleArcLoader);

        for (int i = 0; i < array.length(); i++) {
            int type = array.getIndex(i);

            if (type == R.styleable.SimpleArcLoader_arc_style) {
                String value = array.getString(R.styleable.SimpleArcLoader_arc_style);

                configuration.setLoaderStyle(STYLE.values()[Integer.parseInt(value)]);
            }

            if (type == R.styleable.SimpleArcLoader_arc_colors) {
                int colors_resourse_id = array.getResourceId(R.styleable.SimpleArcLoader_arc_colors, 0);

                if (colors_resourse_id != 0)
                    configuration.setColors(getContext().getResources().getIntArray(colors_resourse_id));
            }

            if (type == R.styleable.SimpleArcLoader_arc_speed) {
                String value = array.getString(R.styleable.SimpleArcLoader_arc_speed);

                if(value != null)
                    configuration.setAnimationSpeedWithIndex(Integer.parseInt(value));
            }

            if (type == R.styleable.SimpleArcLoader_arc_margin) {
                Float value = array.getDimension(R.styleable.SimpleArcLoader_arc_margin, MARGIN_MEDIUM);

                configuration.setArcMargin(value.intValue());
            }

            if(type == R.styleable.SimpleArcLoader_arc_thickness)
            {
                Float value = array.getDimension(R.styleable.SimpleArcLoader_arc_thickness,getContext().getResources().getDimension(R.dimen.stroke_width));
                configuration.setArcWidthInPixel(value.intValue());
            }
        }

        array.recycle();

        return configuration;
    }

    @Override
    public void start() {
        if (mArcDrawable != null)
            mArcDrawable.start();
    }

    @Override
    public void stop() {
        if (mArcDrawable != null)
            mArcDrawable.stop();
    }

    @Override
    public boolean isRunning() {

        if (mArcDrawable != null)
            return mArcDrawable.isRunning();

        return false;
    }

    public void refreshArcLoaderDrawable(ArcConfiguration configuration) {
        if(isRunning())
            stop();

        // Load Drawable for this View
        mArcDrawable = new ArcDrawable(configuration,this);
        setBackgroundDrawable(mArcDrawable);

        // Start the Animation
        start();
    }

    // =============================================================================================
    // CLASS
    // =============================================================================================

    private static class ArcDrawable extends Drawable implements Animatable
    {
        final Runnable updater = new Runnable() {
            @Override
            public void run() {
                mArcAnglePosition += mAnimationSpeed;

                if(mArcAnglePosition > 360)
                    mArcAnglePosition = 0;

                scheduleSelf(updater,FRAME_DURATION + SystemClock.uptimeMillis());
                invalidateSelf();
            }
        };

        ArcConfiguration mConfiguration;
        Paint mPaint;
        int mStrokeWidth,mArcMargin,mArcAnglePosition,mAnimationSpeed;
        int mArcColors[];
        boolean isRunning;
        boolean mDrawCirle;
        WeakReference<View> mViewReference;

        public ArcDrawable(ArcConfiguration configuration,View viewReference) {
            mConfiguration = configuration;
            mViewReference = new WeakReference<View>(viewReference);

            initComponents();
        }

        private void initComponents()
        {
            mStrokeWidth = mConfiguration.getArcWidth();
            mArcMargin = mConfiguration.getArcMargin();
            mArcColors = mConfiguration.getColors();
            mAnimationSpeed = mConfiguration.getAnimationSpeed();
            mDrawCirle = mConfiguration.drawCircle();

            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStrokeWidth(mStrokeWidth);
            mPaint.setStyle(Paint.Style.STROKE);

            // Customize as per Style
            if(mConfiguration.getLoaderStyle() == STYLE.SIMPLE_ARC)
            {
                if(mArcColors.length > 1)
                    mArcColors = new int[]{mArcColors[0],mArcColors[0]};
            }
        }

        @Override
        public void start() {
            if(!isRunning())
            {
                // Set the flag
                isRunning = true;

                scheduleSelf(updater,FRAME_DURATION + SystemClock.uptimeMillis());
                invalidateSelf();
            }
        }

        @Override
        public void stop() {
            if(isRunning())
            {
                // Set the flag
                isRunning = false;

                unscheduleSelf(updater);
                invalidateSelf();
            }
        }

        @Override
        public boolean isRunning() {
            return isRunning;
        }

        @Override
        public void draw(Canvas canvas) {
            View currentView = mViewReference.get();

            if(currentView == null)
                return;

            int w = currentView.getWidth();
            int h = currentView.getHeight();

            int arc1_bound_start = mArcMargin + mStrokeWidth*2;
            int arc_padding = 0;

            if(mDrawCirle)
            {
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setColor(Color.WHITE);
                canvas.drawCircle(w / 2, h / 2, w / 2, mPaint);

                // Reset the configuration
                mPaint.setStyle(Paint.Style.STROKE);

                // Add some padding
                arc_padding +=3;
            }

            RectF arc1_bound = new RectF(arc1_bound_start + arc_padding,arc1_bound_start + arc_padding, ((w-(mStrokeWidth*2)) - mArcMargin ) - arc_padding,((h-(mStrokeWidth*2)) - mArcMargin) - arc_padding);
            RectF arc2_bound = new RectF(mStrokeWidth + arc_padding ,mStrokeWidth + arc_padding,(w-mStrokeWidth) - arc_padding ,( h-mStrokeWidth ) - arc_padding);
            int colors_length = mArcColors.length;

            for(int i = 0 ; i < (colors_length > 4 ? 4 : colors_length ) ; i++ )
            {
                int startangle = (90*i);

                mPaint.setColor(mArcColors[i]);

                canvas.drawArc(arc1_bound,  startangle + mArcAnglePosition, 90 , false, mPaint);
                canvas.drawArc(arc2_bound, startangle - mArcAnglePosition, 90 , false, mPaint);
            }
        }

        @Override
        public void setAlpha(int i) {}

        @Override
        public void setColorFilter(ColorFilter colorFilter) {}

        @Override
        public int getOpacity() {
            return PixelFormat.UNKNOWN;
        }
    }

}
