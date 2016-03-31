package com.leo.simplearcloader;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

/**
 * Created by prathamesh on 16/01/16.
 */
public class ArcConfiguration
{
    // =============================================================================================
    // CONSTANTS
    // =============================================================================================


    // =============================================================================================
    // FIELDS
    // =============================================================================================

    private SimpleArcLoader.STYLE mLoaderStyle;

    private Typeface mTypeFace = null;
    private String mText = "Loading..";
    private int mTextSize = 0;
    private int mTextColor = Color.WHITE;

    private int mArcMargin;
    private int mAnimationSpeed;
    private int mOuterStrokeWidth;
    private int mInnerStrokeWidth;
    private boolean mArcCircle;
    private boolean mArcRounded;
    private int mColors[] = {Color.parseColor("#F90101"), // RED
                             Color.parseColor("#0266C8"), // BLUE
                             Color.parseColor("#F2B50F"), // YELLOW
                             Color.parseColor("#00933B")};// GREEN


    // =============================================================================================
    // CONSTRUCTOR
    // =============================================================================================

    private ArcConfiguration() {}

    public ArcConfiguration(Context context) {

        // Default Values
        mLoaderStyle = SimpleArcLoader.STYLE.SIMPLE_ARC;
        mArcMargin = SimpleArcLoader.MARGIN_MEDIUM;
        mAnimationSpeed = SimpleArcLoader.SPEED_MEDIUM;
        mOuterStrokeWidth = (int) context.getResources().getDimension(R.dimen.stroke_width);
        mInnerStrokeWidth = (int) context.getResources().getDimension(R.dimen.stroke_width);
    }

    public ArcConfiguration(Context context, SimpleArcLoader.STYLE mArcStyle) {
        this(context);

        mLoaderStyle = mArcStyle;
    }

    // =============================================================================================
    // METHODS
    // =============================================================================================

    public SimpleArcLoader.STYLE getLoaderStyle() {
        return mLoaderStyle;
    }

    public void setLoaderStyle(SimpleArcLoader.STYLE mLoaderStyle) {
        this.mLoaderStyle = mLoaderStyle;
    }

    public int getArcMargin() {
        return mArcMargin;
    }

    public void setArcMargin(int mArcMargin) {
        this.mArcMargin = mArcMargin;
    }

    public int getAnimationSpeed() {
        return mAnimationSpeed;
    }

    public void setAnimationSpeed(int mAnimationSpeed) {
        this.mAnimationSpeed = mAnimationSpeed;
    }

    public void setAnimationSpeedWithIndex(int mAnimationIndex) {

        switch (mAnimationIndex) {
            case 0:
                this.mAnimationSpeed = SimpleArcLoader.SPEED_SLOW;
                break;

            case 1:
                this.mAnimationSpeed = SimpleArcLoader.SPEED_MEDIUM;
                break;

            case 2:
                this.mAnimationSpeed = SimpleArcLoader.SPEED_FAST;
                break;
        }
    }

    public int getOuterArcWidth() {
        return mOuterStrokeWidth;
    }

    public void setOuterArcWidthInPixel(int mStrokeWidth) {
        this.mOuterStrokeWidth = mStrokeWidth;
    }

    public int getInnerArcWidth() {
        return mInnerStrokeWidth;
    }

    public void setInnerArcWidthInPixel(int mStrokeWidth) {
        this.mInnerStrokeWidth = mStrokeWidth;
    }

    public boolean isArcRounded() {
        return mArcRounded;
    }

    public void setArcRounded(boolean mArcRounded) {
        this.mArcRounded = mArcRounded;
    }

    public void setColors(int[] colors) {
        if (colors.length > 0)
            mColors = colors;
    }

    public int[] getColors() {
        return mColors;
    }

    public void setTypeFace(Typeface typeFace) {
        mTypeFace = typeFace;
    }

    public Typeface getTypeFace()
    {
        return mTypeFace;
    }

    public void setText(String mText)
    {
        this.mText = mText;
    }

    public String getText()
    {
        return mText;
    }

    public void setTextSize(int size)
    {
        mTextSize = size;
    }

    public int getTextSize()
    {
        return mTextSize;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int mTextColor)
    {
        this.mTextColor = mTextColor;
    }

    public boolean drawCircle()
    {
        return  this.mArcCircle;
    }

    public void drawCircle(boolean drawCircle)
    {
        this.mArcCircle = drawCircle;
    }
}

