package com.dancechar.pilivideo.core.view;

import android.view.Surface;

import com.dancechar.pilivideo.sdk.model.VideoScaleMode;


/**
 * 渲染View公共接口
 * <p>
 * @author netease
 */

public interface IRenderView {

    interface SurfaceCallback {
        void onSurfaceCreated(Surface surface);

        void onSurfaceDestroyed(Surface surface);

        void onSurfaceSizeChanged(Surface surface, int format, int width, int height);
    }

    void onSetupRenderView();

    void setVideoSize(int videoWidth, int videoHeight, int videoSarNum, int videoSarDen, VideoScaleMode scaleMode);

    Surface getSurface();

    void setCallback(SurfaceCallback callback);

    void removeCallback();

    void showView(boolean show);
}
