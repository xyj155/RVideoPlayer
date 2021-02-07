package com.dancechar.pilivideo.sdk.view;

import android.content.Context;
import android.util.AttributeSet;

import com.dancechar.pilivideo.core.view.BaseTextureView;


/**
 * TextureView控件
 * 对TextureView做了封装，可直接用于播放器播放，支持后台播放
 * @author netease
 */

public class AdvanceTextureView extends BaseTextureView {
    public AdvanceTextureView(Context context) {
        super(context);
    }

    public AdvanceTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdvanceTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
