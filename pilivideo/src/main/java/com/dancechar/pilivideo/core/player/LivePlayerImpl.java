package com.dancechar.pilivideo.core.player;

import android.content.Context;

import com.dancechar.pilivideo.sdk.model.VideoOptions;
import com.xyj155.neliveplayer.sdk.NEMediaDataSource;


/**
 * @author xyj155
 */

public class LivePlayerImpl extends AdvanceLivePlayer {

    public LivePlayerImpl(Context context, String videoPath, VideoOptions options) {
        super(context, videoPath, options);
    }

    public LivePlayerImpl(Context context, NEMediaDataSource mediaDataSource, VideoOptions options) {
        super(context, mediaDataSource, options);
    }
}
