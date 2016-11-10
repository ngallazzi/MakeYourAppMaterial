package com.example.xyzreader.ui;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;

public class DynamicHeightImageView extends ImageView {
    private float mAspectRatio = 1.5f;
    private final String TAG = DynamicHeightImageView.class.getSimpleName();
    private final int maxHeight = 300;

    public DynamicHeightImageView(Context context) {
        super(context);
    }

    public DynamicHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicHeightImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAspectRatio(float aspectRatio) {
        mAspectRatio = aspectRatio;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int forecastHeight = (int) (measuredWidth / mAspectRatio);
        setMeasuredDimension(measuredWidth,forecastHeight );
    }
}
