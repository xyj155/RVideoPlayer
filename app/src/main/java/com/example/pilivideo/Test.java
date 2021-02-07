package com.example.pilivideo;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dancechar.pilivideo.controller.CompleteController;

public class Test  extends CompleteController {
    public Test(@NonNull Context context) {
        super(context);
    }

    public Test(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Test(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int layoutId() {
        return R.layout.test_complete;
    }
}
