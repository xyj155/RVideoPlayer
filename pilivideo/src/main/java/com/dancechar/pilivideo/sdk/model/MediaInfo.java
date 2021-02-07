package com.dancechar.pilivideo.sdk.model;

import com.netease.neliveplayer.sdk.model.NEMediaInfo;

/**
 * 媒体信息类
 * @author netease
 */

public class MediaInfo {
    private NEMediaInfo mInner;
    private long mDuration; // 点播

    public MediaInfo(NEMediaInfo mInner, long duration) {
        this.mInner = mInner;
        this.mDuration = duration;
    }

    public long getDuration() {
        return mDuration;
    }

    public String getVideoStreamType() {
        return mInner.mVideoStreamType;
    }

    public String getVideoDecoderMode() {
        return mInner.mVideoDecoderMode;
    }

    public String getAudioStreamType() {
        return mInner.mAudioStreamType;
    }

    public String getAudioDecoderMode() {
        return mInner.mAudioDecoderMode;
    }

    @Override
    public String toString() {
        return "MediaInfo{" +
                "v='" + getVideoStreamType() + '\'' +
                ", '" + getVideoDecoderMode() + '\'' +
                ", a='" + getAudioStreamType() + '\'' +
                ", '" + getAudioDecoderMode() + '\'' +
                ", d='" + mDuration + '\'' +
                '}';
    }
}
