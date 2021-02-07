package com.dancechar.pilivideo.sdk.extension;


import com.dancechar.pilivideo.sdk.LivePlayerObserver;
import com.dancechar.pilivideo.sdk.model.MediaInfo;

/**
 * 播放器状态回调观察者
 * <p>
 * @author xyj155
 */

public abstract class SimpleLivePlayerObserver implements LivePlayerObserver {
    @Override
    public void onPreparing() {

    }

    @Override
    public void onPrepared(MediaInfo mediaInfo) {

    }

    @Override
    public void onError(int code, int extra) {

    }

    @Override
    public void onFirstVideoRendered() {

    }

    @Override
    public void onFirstAudioRendered() {

    }

    @Override
    public void onBufferingStart() {

    }

    @Override
    public void onBufferingEnd() {

    }

    @Override
    public void onBuffering(int percent) {

    }

    @Override
    public void onVideoDecoderOpen(int value){

    }
}
