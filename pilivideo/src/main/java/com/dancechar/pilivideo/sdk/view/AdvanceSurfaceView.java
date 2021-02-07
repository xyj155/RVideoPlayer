package com.dancechar.pilivideo.sdk.view;

import android.content.Context;
import android.util.AttributeSet;

import com.dancechar.pilivideo.core.view.BaseSurfaceView;


/**
 * SurfaceView控件
 * 对SurfaceView做了封装，可直接用于播放器播放
 * @author xyj155
 */

public class AdvanceSurfaceView extends BaseSurfaceView {

    public AdvanceSurfaceView(Context context) {
        super(context);
    }

    public AdvanceSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdvanceSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
