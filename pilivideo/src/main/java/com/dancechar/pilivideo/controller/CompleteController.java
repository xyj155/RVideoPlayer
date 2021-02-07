package com.dancechar.pilivideo.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class CompleteController extends FrameLayout {


    public CompleteController(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public CompleteController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CompleteController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, layoutId(), this);
    }

    public abstract int layoutId();
}
